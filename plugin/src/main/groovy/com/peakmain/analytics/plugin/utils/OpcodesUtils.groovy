package com.peakmain.analytics.plugin.utils

import org.objectweb.asm.Opcodes

class OpcodesUtils implements Opcodes {
    public static final int ASM_VERSION = Opcodes.ASM7
    static boolean isSynthetic(int access) {
        return (access & ACC_SYNTHETIC) != 0
    }

    static boolean isPrivate(int access) {
        return (access & ACC_PRIVATE) != 0
    }

    static boolean isPublic(int access) {
        return (access & ACC_PUBLIC) != 0
    }

    static boolean isStatic(int access) {
        return (access & ACC_STATIC) != 0
    }

    static boolean isNative(int access) {
        return (access & ACC_NATIVE) != 0
    }

    static boolean isAbstract(int access) {
        return (access & ACC_ABSTRACT) != 0
    }
    /**
     * 是否是接口
     */
    static boolean isInterface(int access) {
        return (access & ACC_INTERFACE) != 0
    }
    /**
     * 是否是枚举类型
     */
    static boolean isEnum(int access) {
        return (access & ACC_ENUM) != 0

    }

    static boolean isInitMethod(String name) {
        return name == "<init>"
    }

}
