package com.peakmain.analytics.plugin.utils

class MethodFieldUtils {
    public static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"

    /**
     * MonitorHookMethodConfig的属性
     */
    static String[] EMPTY_INT_RETURN_STRING_DESC = ["()Ljava/lang/String;", "(I)Ljava/lang/String;"]
    static String[] EMPTY_RETURN_WIFI_INFO_DESC = ["()Landroid/net/wifi/WifiInfo;"]
    static String[] EMPTY_RETURN_STRING_DESC=["()Ljava/lang/String;"]


    static String TELEPHONY_MANAGER_CLASS = "android/telephony/TelephonyManager"
    static String GET_DEVICE_ID_METHOD_NAME = "getDeviceId"
    static String GET_MEID_METHOD_NAME = "getMeid"

    static String WIFI_MANAGER_CLASS = "android/net/wifi/WifiManager"
    static String GET_CONNECTION_INFO = "getConnectionInfo"

    static String WIFI_INFO_CLASS = "android/net/wifi/WifiInfo"
    static String GET_MAC_ADDRESS = "getMacAddress"
    //获取时间属性
    static String getTimeFieldName(String methodName) {
        return "timer_" + methodName;
    }
}