package com.peakmain.analytics.plugin.visitor

import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
abstract class MonitorMethodPatternAdapter extends AdviceAdapter{
    protected final static int METHOD_NOTHING_STATE= 0
    protected int state
    MonitorMethodPatternAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM9, mv, access, name, desc)
    }
    @Override
     void visitInsn(int opcode) {
        visitInsn()
        super.visitInsn(opcode)
    }

    @Override
     void visitIntInsn(int opcode, int operand) {
        visitInsn()
        super.visitIntInsn(opcode, operand)
    }

    @Override
     void visitVarInsn(int opcode, int var) {
        visitInsn()
        super.visitVarInsn(opcode, var)
    }

    @Override
     void visitTypeInsn(int opcode, String type) {
        visitInsn()
        super.visitTypeInsn(opcode, type)
    }

    @Override
     void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        visitInsn()
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }

    @Override
     void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
        visitInsn()
        super.visitMethodInsn(opcode, owner, name, descriptor)
    }

    @Override
     void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        visitInsn()
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    @Override
     void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        visitInsn()
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments)
    }

    @Override
     void visitJumpInsn(int opcode, Label label) {
        visitInsn()
        super.visitJumpInsn(opcode, label)
    }

    @Override
     void visitLdcInsn(Object value) {
        visitInsn()
        super.visitLdcInsn(value)
    }

    @Override
     void visitIincInsn(int var, int increment) {
        visitInsn()
        super.visitIincInsn(var, increment)
    }

    @Override
     void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        visitInsn()
        super.visitTableSwitchInsn(min, max, dflt, labels)
    }

    @Override
     void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        visitInsn()
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }

    @Override
     void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        visitInsn()
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
    }

    @Override
     void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        visitInsn()
        super.visitTryCatchBlock(start, end, handler, type)
    }

    @Override
     void visitLabel(Label label) {
        visitInsn()
        super.visitLabel(label)
    }

    @Override
     void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        visitInsn()
        super.visitFrame(type, numLocal, local, numStack, stack)
    }

    @Override
     void visitMaxs(int maxStack, int maxLocals) {
        visitInsn()
        super.visitMaxs(maxStack, maxLocals)
    }

    protected abstract void visitInsn()
}