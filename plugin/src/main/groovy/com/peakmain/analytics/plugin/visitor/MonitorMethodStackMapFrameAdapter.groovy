package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AnalyzerAdapter

/**
 * author ：Peakmain
 * createTime：2022/4/5
 * mail:2726449200@qq.com
 * describe：打印Frame的方法
 */
class MonitorMethodStackMapFrameAdapter extends AnalyzerAdapter {
    private String methodName
    private String methodDesc
    private int methodAccess
    private boolean isLogMethodStackMapFrame = false

    MonitorMethodStackMapFrameAdapter(String owner, int access, String name, String descriptor, MethodVisitor methodVisitor) {
        super(Opcodes.ASM9, owner, access, name, descriptor, methodVisitor)
        this.methodName = name
        this.methodDesc = descriptor
        this.methodAccess = access
    }

    @Override
    void visitCode() {
        super.visitCode()
        if (isLogMethodStackMapFrame) {
            println("Frame的方法：" + methodName + methodDesc)
        }
        printStackMapFrame()
    }

    @Override
    void visitInsn(int opcode) {
        super.visitInsn(opcode)
        printStackMapFrame()
    }

    @Override
    void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand)
        printStackMapFrame()
    }

    @Override
    void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var)
        printStackMapFrame()
    }

    @Override
    void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type)
        printStackMapFrame()
    }

    @Override
    void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor)
        printStackMapFrame()
    }

    @Override
    void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
        printStackMapFrame()
    }

    @Override
    void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments)
        printStackMapFrame()
    }

    @Override
    void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label)
        printStackMapFrame()
    }

    @Override
    void visitLdcInsn(Object value) {
        super.visitLdcInsn(value)
        printStackMapFrame()
    }

    @Override
    void visitIincInsn(int var, int increment) {
        super.visitIincInsn(var, increment)
        printStackMapFrame()
    }

    @Override
    void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels)
        printStackMapFrame()
    }

    @Override
    void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels)
        printStackMapFrame()
    }

    @Override
    void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
        printStackMapFrame()
    }

    @Override
    void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type)
        printStackMapFrame()
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor == "Lcom/peakmain/sdk/annotation/LogMessage;") {
            return new AnnotationVisitor(Opcodes.ASM9) {
                @Override
                void visit(String name, Object value) {
                    super.visit(name, value)
                    if (name == "isLogMethodStackMapFrame") {
                        isLogMethodStackMapFrame = (Boolean) value
                    }
                }
            }
        }
        return super.visitAnnotation(descriptor, visible)
    }

    private void printStackMapFrame() {
        if (!isLogMethodStackMapFrame || OpcodesUtils.isInitMethod(methodName)) return
        //局部变量表
        String locals_str = locals == null ? "[]" : list2Str(locals)
        String stack_str = stack == null ? "[]" : list2Str(stack)
        String line = String.format("%s %s", locals_str, stack_str)
        println("Frame打印:" + line)
    }

    private String list2Str(List<Object> list) {
        int size = list.size()
        String[] array = new String[size]
        for (int i = 0; i < size - 1; i++) {
            Object item = list.get(i)
            array[i] = item2Str(item)
        }
        if (size > 0) {
            int lastIndex = size - 1
            Object item = list.get(lastIndex)
            array[lastIndex] = item2Str(item)
        }
        return Arrays.toString(array)
    }

    private String item2Str(Object obj) {
        if (obj == Opcodes.TOP) {
            return "top"
        } else if (obj == Opcodes.INTEGER) {
            return "int"
        } else if (obj == Opcodes.FLOAT) {
            return "float"
        } else if (obj == Opcodes.DOUBLE) {
            return "double"
        } else if (obj == Opcodes.LONG) {
            return "long"
        } else if (obj == Opcodes.NULL) {
            return "null"
        } else if (obj == Opcodes.UNINITIALIZED_THIS) {
            return "uninitialized_this"
        } else if (obj instanceof Label) {
            Object value = uninitializedTypes.get(obj)
            if (value == null) {
                return obj.toString()
            } else {
                return "uninitialized_" + value
            }
        } else {
            return obj.toString()
        }
    }

}