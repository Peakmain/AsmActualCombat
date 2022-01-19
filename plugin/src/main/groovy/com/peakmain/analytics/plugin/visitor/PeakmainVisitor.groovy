package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.PeakmainHookConfig
import com.peakmain.analytics.plugin.entity.PeakmainMethodCell
import com.peakmain.analytics.plugin.utils.PeakmainUtils
import org.objectweb.asm.*

class PeakmainVisitor extends ClassVisitor {
    private final static String SDK_API_CLASS = "com/peakmain/sdk/SensorsDataAutoTrackHelper"
    private ClassVisitor classVisitor
    private String[] mInterfaces
    private HashMap<String, PeakmainMethodCell> mMethodCells = new HashMap<>()

    PeakmainVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor)
        this.classVisitor = classVisitor
    }
    /**
     * @param version 类版本
     * @param access 修饰符
     * @param name 类名
     * @param signature 泛型信息
     * @param superName 父类
     * @param interfaces 实现的接口
     */
    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.mInterfaces = interfaces
    }
    /**
     * 扫描类的方法进行调用
     * @param access 修饰符
     * @param name 方法名字
     * @param descriptor 方法签名
     * @param signature 泛型信息
     * @param exceptions 抛出的异常
     * @return
     */
    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)

        String nameDesc = name + descriptor
        boolean isSensorsDataTrackViewOnClickAnnotation = false
        boolean isLogMessageTime = false
        methodVisitor = new PeakmainDefaultMethodVisitor(methodVisitor, access, name, descriptor) {
            @Override
            void visitEnd() {
                super.visitEnd()
                if (mMethodCells.containsKey(nameDesc)) {
                    mMethodCells.remove(nameDesc)
                }
            }

            @Override
            void visitInvokeDynamicInsn(String name1, String descriptor1, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
                super.visitInvokeDynamicInsn(name1, descriptor1, bootstrapMethodHandle, bootstrapMethodArguments)
                String desc2 = (String) bootstrapMethodArguments[0]
                PeakmainMethodCell buryPointMethodCell = PeakmainHookConfig.LAMBDA_METHODS.get(Type.getReturnType(descriptor1).getDescriptor() + name1 + desc2)
                if (buryPointMethodCell != null) {
                    Handle it = (Handle) bsmArgs[1]
                    mMethodCells.put(it.name + it.desc, buryPointMethodCell)
                }
            }

            @Override
            void visitCode() {
                super.visitCode()
                if (isLogMessageTime) {
                    getMessageStartCostTime(methodVisitor)
                }
            }


            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode)
                if (isLogMessageTime) {
                    getMessageEndCostTime(methodVisitor, name)
                }
            }

            @Override
            protected void onMethodEnter() {
                super.onMethodEnter()
                /**
                 * 在 android.gradle 的 3.2.1 版本中，针对 view 的 setOnClickListener 方法 的 lambda 表达式做特殊处理。
                 */
                PeakmainMethodCell lambdaMethodCell = mMethodCells.get(nameDesc)
                if (lambdaMethodCell != null) {
                    Type[] types = Type.getArgumentTypes(lambdaMethodCell.desc)
                    int length = types.length
                    Type[] lambdaTypes = Type.getArgumentTypes(descriptor)
                    int paramStart = lambdaTypes.length - length
                    if (paramStart < 0) {
                        return
                    } else {
                        for (int i = 0; i < length; i++) {
                            if (lambdaTypes[paramStart + i].descriptor != types[i].descriptor) {
                                return
                            }
                        }
                    }
                    boolean isStaticMethod = PeakmainUtils.isStatic(access)
                    if (!isStaticMethod) {
                        if (lambdaMethodCell.desc == '(Landroid/view/MenuItem;)Z') {
                            methodVisitor.visitVarInsn(ALOAD, 0)
                            methodVisitor.visitVarInsn(ALOAD, getVisitPosition(lambdaTypes, paramStart, isStaticMethod))
                            methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, lambdaMethodCell.agentName, '(Ljava/lang/Object;Landroid/view/MenuItem;)V', false)
                            return
                        }
                    }

                    for (int i = paramStart; i < paramStart + lambdaMethodCell.paramsCount; i++) {
                        methodVisitor.visitVarInsn(lambdaMethodCell.get(i - paramStart), getVisitPosition(lambdaTypes, i, isStaticMethod))
                    }
                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, lambdaMethodCell.agentName, lambdaMethodCell.agentDesc, false)
                    return
                }

                if (nameDesc == 'onContextItemSelected(Landroid/view/MenuItem;)Z' ||
                        nameDesc == 'onOptionsItemSelected(Landroid/view/MenuItem;)Z') {
                    methodVisitor.visitVarInsn(ALOAD, 0)
                    methodVisitor.visitVarInsn(ALOAD, 1)
                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Ljava/lang/Object;Landroid/view/MenuItem;)V", false)
                }

                if (isSensorsDataTrackViewOnClickAnnotation) {
                    if (desc == '(Landroid/view/View;)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)V", false)
                        return
                    }
                }

                if ((mInterfaces != null && mInterfaces.length > 0)) {
                    if ((mInterfaces.contains('android/view/View$OnClickListener') && nameDesc == 'onClick(Landroid/view/View;)V')) {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)Z", false)
                        methodVisitor.visitVarInsn(ISTORE, 2)
                        Label label1 = new Label()
                        methodVisitor.visitLabel(label1)
                        methodVisitor.visitVarInsn(ILOAD, 2)
                        Label label2 = new Label()
                        methodVisitor.visitJumpInsn(IFNE, label2)
                        Label l3 = new Label()
                        methodVisitor.visitLabel(l3)
                        methodVisitor.visitInsn(RETURN)
                        methodVisitor.visitLabel(label2)
                        Object[] obj = new Object[1]
                        obj[0] = INTEGER
                        methodVisitor.visitFrame(F_APPEND, 1, obj, 0, null)
                        //拦截事件start
                        //boolean flag=false;
                        methodVisitor.visitInsn(ICONST_0)
                        methodVisitor.visitVarInsn(ISTORE, 4)
                        Label label7 = new Label()
                        methodVisitor.visitLabel(label7)
                        //long var3 = System.currentTimeMillis()
                        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                        methodVisitor.visitVarInsn(LSTORE, 3)
                        Label label4 = new Label()
                        methodVisitor.visitLabel(label4)
                        // long lastClickTime =  null == view.getTag() ?  0L : (Long) view.getTag()
                        methodVisitor.visitInsn(ACONST_NULL)
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getTag", "()Ljava/lang/Object;", false)
                        Label label5 = new Label()
                        //null == view.getTag()
                        methodVisitor.visitJumpInsn(IF_ACMPNE, label5)
                        methodVisitor.visitInsn(LCONST_0)
                        Label label6 = new Label()
                        methodVisitor.visitJumpInsn(GOTO, label6)
                        methodVisitor.visitLabel(label5)
                        methodVisitor.visitFrame(F_SAME, 0, null, 0, null)
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getTag", "()Ljava/lang/Object;", false)
                        methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Long")
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false)
                        methodVisitor.visitLabel(label6)
                        Object[] obj3 = new Object[1]
                        obj3[0] = LONG
                        methodVisitor.visitFrame(F_SAME1, 0, null, 1, obj3)
                        methodVisitor.visitVarInsn(LSTORE, 4)
                        Label label8 = new Label()
                        methodVisitor.visitLabel(label8)
                        //if ((curClickTime - lastClickTime) >=  1500){}
                        methodVisitor.visitVarInsn(LLOAD, 3)
                        methodVisitor.visitVarInsn(LLOAD, 4)
                        methodVisitor.visitInsn(LSUB)
                        methodVisitor.visitLdcInsn(new Long(2000L))
                        methodVisitor.visitInsn(LCMP)
                        Label label9 = new Label()
                        methodVisitor.visitJumpInsn(IFLT, label9)
                        //flag=true;
                        methodVisitor.visitInsn(ICONST_1)
                        methodVisitor.visitVarInsn(ISTORE, 4)
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "isClickable", "()Z", false)
                        methodVisitor.visitJumpInsn(IFEQ, label9)
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(LLOAD, 3)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false)
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "setTag", "(Ljava/lang/Object;)V", false)
                        methodVisitor.visitLabel(label9)
                        Object[] obj4 = new Object[1]
                        obj4[0] = LONG
                        methodVisitor.visitFrame(F_APPEND, 1, obj4, 0, null)
                        //if(!flag)return false
                        methodVisitor.visitVarInsn(ILOAD, 4)
                        Label label11 = new Label()
                        methodVisitor.visitJumpInsn(IFNE, label11)
                        methodVisitor.visitInsn(ICONST_0)
                        methodVisitor.visitInsn(IRETURN)
                        methodVisitor.visitLabel(label11)
                        methodVisitor.visitFrame(F_SAME, 0, null, 0, null)


                    } else if (mInterfaces.contains('android/content/DialogInterface$OnClickListener') && nameDesc == 'onClick(Landroid/content/DialogInterface;I)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ILOAD, 2)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/content/DialogInterface;I)V", false)
                    } else if (mInterfaces.contains('android/content/DialogInterface$OnMultiChoiceClickListener') && nameDesc == 'onClick(Landroid/content/DialogInterface;IZ)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ILOAD, 2)
                        methodVisitor.visitVarInsn(ILOAD, 3)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/content/DialogInterface;IZ)V", false)
                    } else if (mInterfaces.contains('android/widget/CompoundButton$OnCheckedChangeListener') && nameDesc == 'onCheckedChanged(Landroid/widget/CompoundButton;Z)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ILOAD, 2)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/widget/CompoundButton;Z)V", false)
                    } else if (mInterfaces.contains('android/widget/RatingBar$OnRatingBarChangeListener') && nameDesc == 'onRatingChanged(Landroid/widget/RatingBar;FZ)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)V", false)
                    } else if (mInterfaces.contains('android/widget/SeekBar$OnSeekBarChangeListener') && nameDesc == 'onStopTrackingTouch(Landroid/widget/SeekBar;)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)V", false)
                    } else if (mInterfaces.contains('android/widget/AdapterView$OnItemSelectedListener') && nameDesc == 'onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ALOAD, 2)
                        methodVisitor.visitVarInsn(ILOAD, 3)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false)
                    } else if (mInterfaces.contains('android/widget/TabHost$OnTabChangeListener') && nameDesc == 'onTabChanged(Ljava/lang/String;)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackTabHost", "(Ljava/lang/String;)V", false)
                    } else if (mInterfaces.contains('android/widget/AdapterView$OnItemClickListener') && nameDesc == 'onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ALOAD, 2)
                        methodVisitor.visitVarInsn(ILOAD, 3)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false)
                    } else if (mInterfaces.contains('android/widget/ExpandableListView$OnGroupClickListener') && nameDesc == 'onGroupClick(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ALOAD, 2)
                        methodVisitor.visitVarInsn(ILOAD, 3)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackExpandableListViewGroupOnClick", "(Landroid/widget/ExpandableListView;Landroid/view/View;I)V", false)
                    } else if (mInterfaces.contains('android/widget/ExpandableListView$OnChildClickListener') && nameDesc == 'onChildClick(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitVarInsn(ALOAD, 2)
                        methodVisitor.visitVarInsn(ILOAD, 3)
                        methodVisitor.visitVarInsn(ILOAD, 4)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackExpandableListViewChildOnClick", "(Landroid/widget/ExpandableListView;Landroid/view/View;II)V", false)
                    }
                }

            }

            @Override
            AnnotationVisitor visitAnnotation(String s, boolean b) {
                if (s == "Lcom/peakmain/sdk/SensorsDataTrackViewOnClick;") {
                    isSensorsDataTrackViewOnClickAnnotation = true
                }
                if (s == "Lcom/peakmain/sdk/utils/LogMessageTime;") {
                    println("进来了")
                    isLogMessageTime = true
                }
                return super.visitAnnotation(s, b)
            }
        }
        return methodVisitor
    }

    /**
     * 获取方法参数下标为 index 的对应 ASM index
     * @param types 方法参数类型数组
     * @param index 方法中参数下标，从 0 开始
     * @param isStaticMethod 该方法是否为静态方法
     * @return 访问该方法的 index 位参数的 ASM index
     */
    int getVisitPosition(Type[] types, int index, boolean isStaticMethod) {
        if (types == null || index < 0 || index >= types.length) {
            throw new Error("getVisitPosition error")
        }
        if (index == 0) {
            return isStaticMethod ? 0 : 1
        } else {
            return getVisitPosition(types, index - 1, isStaticMethod) + types[index - 1].getSize()
        }
    }

    private void getMessageStartCostTime(MethodVisitor methodVisitor) {
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        methodVisitor.visitVarInsn(LSTORE, 1)
        Label label1 = new Label()
        methodVisitor.visitLabel(label1)
    }

    private void getMessageEndCostTime(MethodVisitor methodVisitor, String name) {
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        methodVisitor.visitVarInsn(LLOAD, 1)
        methodVisitor.visitInsn(LSUB)
        methodVisitor.visitVarInsn(LSTORE, 2)
        Label label2 = new Label()
        methodVisitor.visitLabel(label2)
        methodVisitor.visitLdcInsn("LogMessageCostTime")
        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder")
        methodVisitor.visitInsn(DUP)
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
        methodVisitor.visitLdcInsn(name + "消耗的时间:")
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        methodVisitor.visitVarInsn(LLOAD, 2)
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
        methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false)
        methodVisitor.visitInsn(POP)
        Label label3 = new Label()
        methodVisitor.visitLabel(label3)
    }
}