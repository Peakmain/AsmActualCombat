package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.entity.PeakmainMethodCell
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * author ：Peakmain
 * createTime：2021/1/5
 * mail:2726449200@qq.com
 * describe：
 */
class PeakmainVisitor extends ClassVisitor {
    private ClassVisitor classVisitor
    private String[] mInterfaces
    private HashMap<String, PeakmainMethodCell> mMethodCells = new HashMap<>()
    private String mClassName
    private MonitorConfig mMonitorConfig
    private String mTargetOwner = "android/telephony/TelephonyManager"
    //private String mMethodOwner = "com/peakmain/asmactualcombat/utils/Utils"
    private String mTargetName = "getDeviceId"
    private String mTargetDesc = "()Ljava/lang/String;"
    private String mTargetDesc1 = "(I)Ljava/lang/String;"

    private  HashMap<String,MethodCalledBean> methodCalledBeans = new HashMap<>()

    PeakmainVisitor(ClassVisitor classVisitor, MonitorConfig config) {
        super(Opcodes.ASM9, classVisitor)
        this.classVisitor = classVisitor
        this.mMonitorConfig = config
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
        this.mInterfaces = interfaces
        this.mClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
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
        methodVisitor = new MonitorClickAdapter(methodVisitor, access, name, descriptor, mMethodCells, mInterfaces)
        methodVisitor = new MonitorMethodTimeAdapter(methodVisitor, access, name, descriptor, mClassName, classVisitor)
        methodVisitor = new MonitorPrintParametersReturnValueAdapter(methodVisitor, access, name, descriptor, mClassName, classVisitor)
     /*   if (methodVisitor != null && !OpcodesUtils.isInitMethod(name) && !OpcodesUtils.isNative(access) && !OpcodesUtils.isAbstract(access)) {
            methodVisitor = new MonitorDefalutMethodAdapter(methodVisitor, access, name, descriptor) {
                @Override
                void visitMethodInsn(int opcodeAndSource, String owner, String methodName, String desc, boolean isInterface) {
                    if (mTargetOwner == owner && methodName == mTargetName && (desc == mTargetDesc || mTargetDesc1 == desc)) {
                        println(methodName + "------" +descriptor+ "--" + mClassName)
                        methodCalledBeans.put(mClassName+methodName+desc,new MethodCalledBean(mClassName, access, methodName, desc))
                    }
                    super.visitMethodInsn(opcodeAndSource, owner, methodName, desc, isInterface)
                }

                @Override
                void visitEnd() {
                    for (Map.Entry<Integer, Integer> entry : methodCalledBeans.entrySet()) {
                        println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString())
                    }
                    super.visitEnd()
                }
            }

        }*/
        methodVisitor=new MonitorMethodCalledAdapter(methodVisitor,access,name,descriptor,mClassName,mMonitorConfig)
        return methodVisitor
    }

    static void clearMethodBody(MethodVisitor mv, String className, int access, String name, String descriptor) {
        Type type = Type.getType(descriptor)
        Type[] argumentsType = type.getArgumentTypes()
        Type returnType = type.getReturnType()
        int stackSize = returnType.getSize()
        int localSize = OpcodesUtils.isStatic(access) ? 0 : 1
        for (Type argType : argumentsType) {
            localSize += argType.size
        }
        mv.visitCode()
        if (returnType.getSort() == Type.VOID) {
            mv.visitInsn(Opcodes.RETURN)
        } else if (returnType.getSort() >= Type.BOOLEAN && returnType.getSort() <= Type.DOUBLE) {
            mv.visitInsn(returnType.getOpcode(Opcodes.ICONST_1))
            mv.visitInsn(returnType.getOpcode(Opcodes.IRETURN))
        } else {
            mv.visitInsn(Opcodes.ACONST_NULL)
            mv.visitInsn(Opcodes.ARETURN)
        }
        mv.visitMaxs(stackSize, localSize)
        mv.visitEnd()
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
