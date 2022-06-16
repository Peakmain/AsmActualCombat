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
    public static final String NAME_MOB_POST_HTTP = "httpPost"
    public static final String DESCRIPTOR_MOB_GET_HTTP =
            '(Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)Ljava/lang/String;'

    public static final String DESCRIPTOR_MOB_RAW_GET_HTTP =
            '(Ljava/lang/String;Ljava/util/ArrayList;Lcom/mob/tools/network/RawNetworkCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    public static final String DESCRIPTOR_MOB_POST_HTTP =
            '(Ljava/lang/String;Ljava/util/ArrayList;[BLjava/util/ArrayList;ILcom/mob/tools/network/HttpResponseCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    public static final String DESCRIPTOR_MOB_POST_HTTP1 =
            '(Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;ILcom/mob/tools/network/HttpResponseCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    public static final String DESCRIPTOR_MOB_POST_HTTP2 =
            '(Ljava/lang/String;Ljava/util/ArrayList;ILcom/mob/tools/network/HttpResponseCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    //glideUrl
    public static final String OWNER_GLIDE_URL_HTTP = "com/bumptech/glide/load/model/GlideUrl"
    public static final String NAME_GLIDE_URL_HTTP = "getHeaders"
    public static final String DESCRIPTOR_GLIDE_URL_HTTP = "()Ljava/util/Map;"
    //替换的类和方法
    public static final String OWNER_NEW_QI_NIU_HTTP_USER_AGENT = "com/peakmain/sdk/utils/network/UserAgent"
    public static final String OWNER_NEW_GLIDE_URL_HTTP = "com/peakmain/sdk/utils/network/NetworkGlide"
    public static final String DESCRIPTOR_NEW_QI_NIU_HTTP_USER_AGENT = "(Lcom/qiniu/android/http/UserAgent;Ljava/lang/String;)Ljava/lang/String;"
    public static final String OWNER_NEW_MOB_HTTP = "com/peakmain/sdk/utils/network/Mob"
    public static final String DESCRIPTOR_NEW_MOB_GET_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)Ljava/lang/String;'

    public static final String DESCRIPTOR_NEW_MOB_RAW_GET_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;Lcom/mob/tools/network/RawNetworkCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    public static final String DESCRIPTOR_NEW_MOB_POST_HTTP =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;[BLjava/util/ArrayList;ILcom/mob/tools/network/HttpResponseCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    public static final String DESCRIPTOR_NEW_MOB_POST_HTTP1 =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;ILcom/mob/tools/network/HttpResponseCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'
    public static final String DESCRIPTOR_NEW_MOB_POST_HTTP2 =
            '(Lcom/mob/tools/network/NetworkHelper;Ljava/lang/String;Ljava/util/ArrayList;ILcom/mob/tools/network/HttpResponseCallback;Lcom/mob/tools/network/NetworkHelper$NetworkTimeOut;)V'


    public static final String DESCRIPTOR_NEW_GLIDE_URL_HTTP = "(Lcom/bumptech/glide/load/model/GlideUrl;)Ljava/util/Map;"
    static String[] QI_NIU_METHOD_DESCRIPTOR = [DESCRIPTOR_QI_NIU_HTTP_USER_AGENT]
    static String[] MOB_GET_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_GET_HTTP]
    static String[] MOB_RAW_GET_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_RAW_GET_HTTP]
    static String[] GLIDE_METHOD_DESCRIPTOR = [DESCRIPTOR_GLIDE_URL_HTTP]
    static String[] MOB_POST_METHOD_DESCRIPTOR = [DESCRIPTOR_MOB_POST_HTTP,DESCRIPTOR_MOB_POST_HTTP1,DESCRIPTOR_MOB_POST_HTTP2]
}