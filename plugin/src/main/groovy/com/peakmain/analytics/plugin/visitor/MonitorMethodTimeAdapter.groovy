package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.utils.OpcodesUtils
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：获取方法耗时时间
 */
class MonitorMethodTimeAdapter extends MonitorDefalutMethodAdapter {
    private String mClassName
    private boolean isLogMessageTime = false
    private String mMethodName
    private int mMethodAccess
    private String mFieldDescriptor = "J"
    private ClassWriter mClassWriter
    private static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"
    /**
     * Constructs a new {@link MonitorMethodTimeAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorMethodTimeAdapter(MethodVisitor mv, int access, String name, String desc, String className, ClassWriter cv) {
        super(mv, access, name, desc)
        mClassName = className
        mMethodName = name
        mClassWriter = cv
        mMethodAccess = access
    }

    @Override
    void visitCode() {
        if (isLogMessageTime && !OpcodesUtils.isNative(mMethodAccess) && !OpcodesUtils.isAbstract(mMethodAccess) && !OpcodesUtils.isInitMethod(mMethodName)) {
            FieldVisitor fv = mClassWriter.visitField(ACC_PUBLIC | ACC_STATIC, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor, null, null)
            if (fv != null) {
                fv.visitEnd()
            }
        }
        super.visitCode()
    }

    @Override
    protected void onMethodEnter() {
        if (isLogMessageTime && !OpcodesUtils.isNative(mMethodAccess) && !OpcodesUtils.isAbstract(mMethodAccess) && !OpcodesUtils.isInitMethod(mMethodName)) {
            mv.visitFieldInsn(GETSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            mv.visitInsn(LSUB)
            mv.visitFieldInsn(PUTSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
        }
        super.onMethodEnter()
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (isLogMessageTime && !OpcodesUtils.isNative(mMethodAccess) && !OpcodesUtils.isAbstract(mMethodAccess) && !OpcodesUtils.isInitMethod(mMethodName)) {
            mv.visitFieldInsn(GETSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            mv.visitInsn(LADD)
            mv.visitFieldInsn(PUTSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
            mv.visitFieldInsn(GETSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
            mv.visitMethodInsn(INVOKESTATIC,LOG_MANAGER,"println","(J)V",false)
        }
        super.onMethodExit(opcode)
    }

    @Override
    AnnotationVisitor visitAnnotation(String s, boolean b) {
        if (s == "Lcom/peakmain/sdk/utils/LogMessageTime;") {
            isLogMessageTime = true
        }
        return super.visitAnnotation(s, b)
    }
}