package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.ext.MonitorHookMethodConfig
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：方法被调用，然后清空被调用的方法
 */
class MonitorMethodCalledClearAdapter extends MonitorDefalutMethodAdapter {
    private String mClassName

    private int mAccess
    private MonitorConfig monitorConfig
    private String mDesc
    /**
     * Constructs a new {@link MonitorMethodCalledClearAdapter}.
     *
     * @param mv
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorMethodCalledClearAdapter(MethodVisitor mv, int access, String name, String desc, String className, MonitorConfig monitorConfig) {
        super(mv, access, name, desc)
        mClassName = className
        mAccess = access
        mDesc = desc
        this.monitorConfig = monitorConfig
    }

    @Override
    void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        HashMap<String, MethodCalledBean> methodCalledBeans = MonitorHookMethodConfig.methodCalledBeans
        if (!monitorConfig.whiteList.contains(mClassName) && !monitorConfig.exceptSet.contains(mClassName) && methodCalledBeans.containsKey(owner + name + descriptor)) {
            println("调用方法的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            clearMethodBody(mv, mClassName, access, name, descriptor,mDesc)
            return
        }
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
    }


    static void clearMethodBody(MethodVisitor mv, String className, int access, String name, String descriptor,String methodDescriptor) {
        Type type = Type.getType(descriptor)
        Type methodType = Type.getType(methodDescriptor)
        Type methodReturnType = methodType.getReturnType()
        Type[] argumentsType = type.getArgumentTypes()
        Type returnType = type.getReturnType()
        int stackSize = returnType.getSize()
        int localSize = OpcodesUtils.isStatic(access) ? 0 : 1
        for (Type argType : argumentsType) {
            localSize += argType.size
        }
        mv.visitCode()
        if (methodReturnType.getSort() == Type.VOID) {
            mv.visitInsn(RETURN)
        } else if (methodReturnType.getSort() >= Type.BOOLEAN && methodReturnType.getSort() <= Type.DOUBLE) {
            mv.visitInsn(methodReturnType.getOpcode(ICONST_1))
            mv.visitInsn(methodReturnType.getOpcode(IRETURN))
        } else if (methodReturnType.getInternalName() == "java/lang/String") {
            mv.visitLdcInsn("")
            mv.visitInsn(ARETURN)
        } else {
            mv.visitInsn(ACONST_NULL)
            mv.visitInsn(ARETURN)
        }
        mv.visitMaxs(stackSize, localSize)
        mv.visitEnd()
    }
}
