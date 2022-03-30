package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.utils.OpcodesUtils
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
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
    private ArrayList<String> result = new ArrayList<>()
    private static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"
    private boolean isLogParametersReturnValue = false
    /**
     * Constructs a new {@link MonitorDefalutMethodAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorPrintParametersReturnValueAdapter(MethodVisitor mv, int access, String name, String desc, String className) {
        super(mv, access, name, desc)
        this.mMethodName = name
        this.mClassName = className
        this.mDescriptor = desc
        this.mMethodAccess = access
    }

    @Override
    protected void onMethodEnter() {
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

        super.onMethodEnter()
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

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean b) {
        if (descriptor == "Lcom/peakmain/sdk/annotation/LogParametersReturnValue;") {
            isLogParametersReturnValue = true
        }
        return super.visitAnnotation(descriptor, b)
    }

    private void printMessage(String value) {
        mv.visitLdcInsn(value)
        mv.visitMethodInsn(INVOKESTATIC, LOG_MANAGER, "printlnStr", "(Ljava/lang/String;)V", false)
    }
}