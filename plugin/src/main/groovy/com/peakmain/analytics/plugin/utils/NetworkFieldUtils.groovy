package com.peakmain.analytics.plugin.utils

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class NetworkFieldUtils {
    public static final String OWNER_QI_NIU_HTTP_USER_AGENT = "com/qiniu/android/http/UserAgent"
    public static final String NAME_QI_NIU_HTTP_USER_AGENT = "getUa"
    public static final String DESCRIPTOR_QI_NIU_HTTP_USER_AGENT = "(Ljava/lang/String;)Ljava/lang/String;"


    //替换的类和方法
    public static final String OWNER_NEW_QI_NIU_HTTP_USER_AGENT = "com/peakmain/sdk/utils/network/UserAgent"
    public static final String DESCRIPTOR_NEW_QI_NIU_HTTP_USER_AGENT = "(Lcom/qiniu/android/http/UserAgent;Ljava/lang/String;)Ljava/lang/String;"


}