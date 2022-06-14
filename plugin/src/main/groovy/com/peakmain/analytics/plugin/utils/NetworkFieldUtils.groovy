package com.peakmain.analytics.plugin.utils

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class NetworkFieldUtils {
    //七牛云
    public static final String OWNER_QI_NIU_HTTP_USER_AGENT = "com/qiniu/android/http/UserAgent"
    public static final String NAME_QI_NIU_HTTP_USER_AGENT = "getUa"
    public static final String DESCRIPTOR_QI_NIU_HTTP_USER_AGENT = "(Ljava/lang/String;)Ljava/lang/String;"

    //mob
    public static final String OWNER_MOB_HTTP = "com/mob/tools/network/NetworkHelper"
    public static final String NAME_MOB_HTTP = "httpGet"
    public static final String DESCRIPTOR_MOB_HTTP =
            '(Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)Ljava/lang/String;'

    //替换的类和方法
    public static final String OWNER_NEW_QI_NIU_HTTP_USER_AGENT = "com/peakmain/sdk/utils/network/UserAgent"
    public static final String DESCRIPTOR_NEW_QI_NIU_HTTP_USER_AGENT = "(Lcom/qiniu/android/http/UserAgent;Ljava/lang/String;)Ljava/lang/String;"
    public static final String OWNER_NEW_MOB_HTTP = "com/peakmain/sdk/utils/network/Mob"
    public static final String DESCRIPTOR_NEW_MOB_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)Ljava/lang/String;'

    static String[] QI_NIU_METHOD_DESCRIPTOR = [DESCRIPTOR_QI_NIU_HTTP_USER_AGENT]
    static String[] MOB_GET_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_HTTP]
}