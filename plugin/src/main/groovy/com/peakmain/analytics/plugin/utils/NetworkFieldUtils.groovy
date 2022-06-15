package com.peakmain.analytics.plugin.utils

class NetworkFieldUtils {
    //七牛云
    public static final String OWNER_QI_NIU_HTTP_USER_AGENT = "com/qiniu/android/http/UserAgent"
    public static final String NAME_QI_NIU_HTTP_USER_AGENT = "getUa"
    public static final String DESCRIPTOR_QI_NIU_HTTP_USER_AGENT = "(Ljava/lang/String;)Ljava/lang/String;"

    //mob
    public static final String OWNER_MOB_HTTP = "com/mob/tools/network/NetworkHelper"
    public static final String NAME_MOB_GET_HTTP = "httpGet"
    public static final String NAME_MOB_RAW_GET_HTTP = "rawGet"
    public static final String NAME_MOB_JSON_POST_HTTP = "jsonPost"
    public static final String DESCRIPTOR_MOB_GET_HTTP =
            '(Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)Ljava/lang/String;'

    public static final String DESCRIPTOR_MOB_RAW_GET_HTTP =
            '(Ljava/lang/String;Ljava/util/ArrayList;Lcom/mob/tools/network/RawNetworkCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'

    public static final String DESCRIPTOR_MOB_JSON_POST_HTTP =
            '(Ljava/lang/String;Ljava/util/HashMap;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;Lcom/mob/tools/network/HttpResponseCallback;)V'
    //替换的类和方法
    public static final String OWNER_NEW_QI_NIU_HTTP_USER_AGENT = "com/peakmain/sdk/utils/network/UserAgent"
    public static final String DESCRIPTOR_NEW_QI_NIU_HTTP_USER_AGENT = "(Lcom/qiniu/android/http/UserAgent;Ljava/lang/String;)Ljava/lang/String;"
    public static final String OWNER_NEW_MOB_HTTP = "com/peakmain/sdk/utils/network/Mob"
    public static final String DESCRIPTOR_NEW_MOB_GET_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)Ljava/lang/String;'

    public static final String DESCRIPTOR_NEW_MOB_RAW_GET_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;Lcom/mob/tools/network/RawNetworkCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'

    public static final String DESCRIPTOR_NEW_MOB_JSON_POST_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/HashMap;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;Lcom/mob/tools/network/HttpResponseCallback;)V'

    static String[] QI_NIU_METHOD_DESCRIPTOR = [DESCRIPTOR_QI_NIU_HTTP_USER_AGENT]
    static String[] MOB_GET_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_GET_HTTP]
    static String[] MOB_RAW_GET_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_RAW_GET_HTTP]
    static String[] MOB_JSON_POST_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_JSON_POST_HTTP]
}