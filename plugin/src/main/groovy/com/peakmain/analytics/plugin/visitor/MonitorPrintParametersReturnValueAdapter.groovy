package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * author ：Peakmain
 * createTime：2022/3/30
 * mail:2726449200@qq.com
 * describe：打印方法参数和返回值
 */
class MonitorPrintParametersReturnValueAdapter extends MonitorDefalutMethodAdapter {
    private String mClassName
    private String mMethodName
    private String mDescriptor
    private int mMethodAccess
    private static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"
    private boolean isLogParametersReturnValue = false
    private boolean isLogMessageTime = false
    private String mFieldDescriptor = "J"
    private ClassWriter mClassWriter
    /**
     * Constructs a new {@link MonitorDefalutMethodAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorPrintParametersReturnValueAdapter(MethodVisitor mv, int access, String name, String desc, String className, ClassWriter cv) {
        super(mv, access, name, desc)
        this.mMethodName = name
        this.mClassName = className
        this.mDescriptor = desc
        this.mMethodAccess = access
        this.mClassWriter = cv
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
        super.onMethodEnter()
        if (isLogParametersReturnValue && !OpcodesUtils.isNative(mMethodAccess) && !OpcodesUtils.isAbstract(mMethodAccess) && !OpcodesUtils.isInitMethod(mMethodName)) {
            def isStatic = OpcodesUtils.isStatic(mMethodAccess)
            int slotIndex = isStatic ? 0 : 1
            Type methodType = Type.getMethodType(mDescriptor)
            Type[] argumentsTypes = methodType.getArgumentTypes()
            for (Type type : argumentsTypes) {
                int sort = type.getSort()
                int size = type.size
                String desc = type.getDescriptor()
                int opcode = type.getOpcode(ILOAD)
                mv.visitVarInsn(opcode, slotIndex)
                if (sort >= Type.BOOLEAN && sort <= Type.DOUBLE) {
                    String methodDesc = String.format("(%s)V", desc)
                    printValueOnStack(methodDesc)
                } else {
                    printValueOnStack("(Ljava/lang/Object;)V");
                }
                slotIndex += size
            }
        }
        if (isLogMessageTime && !OpcodesUtils.isNative(mMethodAccess) && !OpcodesUtils.isAbstract(mMethodAccess) && !OpcodesUtils.isInitMethod(mMethodName)) {
            mv.visitFieldInsn(GETSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            mv.visitInsn(LSUB)
            mv.visitFieldInsn(PUTSTATIC, mClassName, MethodFieldUtils.getTimeFieldName(mMethodName), mFieldDescriptor)
        }

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
    void visitInsn(int opcode) {
        if (isLogParametersReturnValue && !OpcodesUtils.isNative(mMethodAccess) && !OpcodesUtils.isAbstract(mMethodAccess) && !OpcodesUtils.isInitMethod(mMethodName)) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                if (opcode >= IRETURN && opcode <= DRETURN) {
                    Type methodType = Type.getMethodType(descriptor)
                    Type returnType = methodType.getReturnType()
                    int size = returnType.getSize()
                    String desc = returnType.getDescriptor()
                    if (size == 1) {
                        mv.visitInsn(DUP)
                    } else {
                        mv.visitInsn(DUP2)
                    }
                    String methodDesc = String.format("(%s)V", desc)
                    printValueOnStack(methodDesc);
                } else if (opcode == ARETURN) {
                    mv.visitInsn(DUP)
                    printValueOnStack("(Ljava/lang/Object;)V");
                } else if (opcode == RETURN) {
                    printMessage("return void ")
                } else {
                    printMessage("return abnormal")
                }
            }
        }
        super.visitInsn(opcode)
    }

    private void printValueOnStack(String value) {
        mv.visitMethodInsn(INVOKESTATIC, LOG_MANAGER, "println", value, false);
    }
    /**
     * 该方法是当扫描器扫描到类注解声明时进行调用
     *
     * @param s 注解的类型。它使用的是（“L” + “类型路径” + “;”）形式表述
     * @param b 表示的是，该注解是否在 JVM 中可见
     * 1.RetentionPolicy.SOURCE：声明注解只保留在 Java 源程序中，在编译 Java 类时注解信息不会被写入到 Class。如果使用的是这个配置 ASM 也将无法探测到这个注解。
     * 2.RetentionPolicy.CLASS：声明注解仅保留在 Class 文件中，JVM 运行时并不会处理它，这意味着 ASM 可以在 visitAnnotation 时候探测到它，但是通过Class 反射无法获取到注解信息。
     * 3.RetentionPolicy.RUNTIME：这是最常用的一种声明，ASM 可以探测到这个注解，同时 Java 反射也可以取得注解的信息。所有用到反射获取的注解都会用到这个配置，就是这个原因。
     * @return
     */
    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean b) {
        if (descriptor == "Lcom/peakmain/sdk/annotation/LogMessage;") {
            return new AnnotationVisitor(OpcodesUtils.ASM_VERSION) {
                @Override
                void visit(String name, Object value) {
                    super.visit(name, value)
                    if (name == "isLogTime") {
                        isLogMessageTime = (Boolean) value
                    } else if (name == "isLogParametersReturnValue") {
                        isLogParametersReturnValue = (Boolean) value
                    }
                }
            }
        }
        return super.visitAnnotation(descriptor, b)
    }

    private void printMessage(String value) {
        mv.visitLdcInsn(value)
        mv.visitMethodInsn(INVOKESTATIC, LOG_MANAGER, "printlnStr", "(Ljava/lang/String;)V", false)
    }

}