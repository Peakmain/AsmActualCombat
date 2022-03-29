package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.PeakmainHookConfig
import com.peakmain.analytics.plugin.entity.PeakmainMethodCell
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：点击事件的Visitor
 */
class MonitorClickAdapter extends MonitorDefalutMethodAdapter {
    private final static String SDK_API_CLASS = "com/peakmain/sdk/SensorsDataAutoTrackHelper"
    private HashMap<String, PeakmainMethodCell> mMethodCells = new HashMap<>()
    private String nameDesc
    private MethodVisitor methodVisitor
    boolean isSensorsDataTrackViewOnClickAnnotation = false
    private String descriptor
    private String[] mInterfaces
    /**
     * Constructs a new {@link MonitorDefalutMethodAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorClickAdapter(MethodVisitor mv, int access, String name, String desc, HashMap<String, PeakmainMethodCell> map, String[] interfaces) {
        super(mv, access, name, desc)
        mMethodCells = map
        nameDesc = name + desc
        methodVisitor = mv
        descriptor = desc
        mInterfaces = interfaces
    }

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
        PeakmainMethodCell peakmainMethodCell = PeakmainHookConfig.LAMBDA_METHODS.get(Type.getReturnType(descriptor1).getDescriptor() + name1 + desc2)
        if (peakmainMethodCell != null) {
            Handle it = (Handle) bootstrapMethodArguments[1]
            mMethodCells.put(it.name + it.desc, peakmainMethodCell)
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
            boolean isStaticMethod = OpcodesUtils.isStatic(access)
            if (!isStaticMethod) {
                if (lambdaMethodCell.desc == '(Landroid/view/MenuItem;)Z') {
                    methodVisitor.visitVarInsn(ALOAD, 0)
                    methodVisitor.visitVarInsn(ALOAD, getVisitPosition(lambdaTypes, paramStart, isStaticMethod))
                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, lambdaMethodCell.agentName, '(Ljava/lang/Object;Landroid/view/MenuItem;)V', false)
                    return
                }
            }

            for (int i = paramStart; i < paramStart + lambdaMethodCell.paramsCount; i++) {
                methodVisitor.visitVarInsn(lambdaMethodCell.opcodes.get(i - paramStart), getVisitPosition(lambdaTypes, i, isStaticMethod))
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
            if (descriptor == '(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)Z", false)
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
                methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)Z", false)
            } else if (mInterfaces.contains('android/widget/SeekBar$OnSeekBarChangeListener') && nameDesc == 'onStopTrackingTouch(Landroid/widget/SeekBar;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)Z", false)
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
        return super.visitAnnotation(s, b)
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

}