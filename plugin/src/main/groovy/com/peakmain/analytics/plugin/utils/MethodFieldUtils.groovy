package com.peakmain.analytics.plugin.utils

class MethodFieldUtils {
    public static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"

    /**
     * MonitorHookMethodConfig的属性
     */
    //class
    static String WIFI_MANAGER_CLASS = "android/net/wifi/WifiManager"
    static String WIFI_INFO_CLASS = "android/net/wifi/WifiInfo"
    static String TELEPHONY_MANAGER_CLASS = "android/telephony/TelephonyManager"
    static String SETTINGS_SECURE_CLASS = 'android/provider/Settings$Secure'
    //methodName
    static String GET_DEVICE_ID_METHOD_NAME = "getDeviceId"
    static String GET_MEID_METHOD_NAME = "getMeid"
    static String GET_CONNECTION_INFO_METHOD_NAME = "getConnectionInfo"
    static String GET_MAC_ADDRESS_METHOD_NAME = "getMacAddress"
    static String GET_IP_ADDRESS_METHOD_NAME = "getIpAddress"
    static String GET_STRING_METHOD_NAME = "getString"
    //desc
    static String[] EMPTY_INT_RETURN_STRING_DESC = ["()Ljava/lang/String;", "(I)Ljava/lang/String;"]
    static String[] EMPTY_RETURN_WIFI_INFO_DESC = ["()Landroid/net/wifi/WifiInfo;"]
    static String[] EMPTY_RETURN_STRING_DESC = ["()Ljava/lang/String;"]
    static String[] EMPTY_RETURN_INT_DESC = ["()I"]
    static String[] CONTENTRESOLVER_STRING_RETURN_STRING_DESC = ["(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;"]


    //获取时间属性
    static String getTimeFieldName(String methodName) {
        return "timer_" + methodName
    }
}