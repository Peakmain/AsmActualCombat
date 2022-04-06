package com.peakmain.analytics.plugin.utils

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * author ：Peakmain
 * createTime：2022/4/6
 * mail:2726449200@qq.com
 * describe：StringBuilder的工具类
 */
class StringBuilderUtils {
    private MethodVisitor mv

    StringBuilderUtils(MethodVisitor methodVisitor) {
        this.mv = methodVisitor
    }

    void appendLdcString(String value) {
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder")
        mv.visitInsn(Opcodes.DUP)
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn(value)
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
    }
}