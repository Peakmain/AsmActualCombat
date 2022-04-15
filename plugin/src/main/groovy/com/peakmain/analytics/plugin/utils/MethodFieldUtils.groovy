package com.peakmain.analytics.plugin.utils

import org.objectweb.asm.Opcodes

class MethodFieldUtils {
    public static final String PLUGIN_VERSION = "1.0.7"
    enum StatusEnum {
        METHOD_STATE_NORMAL(1),
        METHOD_STATE_CLEAR(2),
        METHOD_STATE_REPLACE(3)
        private int value

        StatusEnum(int value) {
            this.value = value
        }

        int getValue() {
            return value
        }
    }


    public static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"
    public static final String NEW_METHOD_OWNER = "com/peakmain/sdk/utils/ReplaceMethodUtils"
    public static final int STATIC_OPCODE = Opcodes.INVOKESTATIC
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
    static final String EMPTY_STRING_DESC = "()Ljava/lang/String;"
    static final String EMPTY_STRING_WIFI_INFO_DESC = "(Landroid/net/wifi/WifiInfo;)Ljava/lang/String;"
    static final String NEW_EMPTY_STRING_TELEPHONY_DESC = "(Landroid/telephony/TelephonyManager;)Ljava/lang/String;"
    static final String INT_STRING_DESC = "(I)Ljava/lang/String;"
    static final String NEW_INT_STRING_TELEPHONY_DESC = "(Landroid/telephony/TelephonyManager;I)Ljava/lang/String;"
    static String[] EMPTY_INT_RETURN_STRING_DESC = [EMPTY_STRING_DESC, INT_STRING_DESC]
    static final String EMPTY_WIFI_INFO_DESC = "()Landroid/net/wifi/WifiInfo;"
    static final String EMPTY_WIFI_INFO_WIFI_INFO_DESC = "(Landroid/net/wifi/WifiManager;)Landroid/net/wifi/WifiInfo;"
    static String[] EMPTY_RETURN_WIFI_INFO_DESC = [EMPTY_WIFI_INFO_DESC]
    static String[] EMPTY_RETURN_STRING_DESC = [EMPTY_STRING_DESC]
    static final String EMPTY_INT_DESC = "()I"
    static final String EMPTY_INT_WIFI_INFO_DESC = "(Landroid/net/wifi/WifiInfo;)I"
    static String[] EMPTY_RETURN_INT_DESC = [EMPTY_INT_DESC]
    static final String CONTENTRESOLVER_STRING_DESC = "(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;"
    static String[] CONTENTRESOLVER_STRING_RETURN_STRING_DESC = [CONTENTRESOLVER_STRING_DESC]


    //获取时间属性
    static String getTimeFieldName(String methodName) {
        return "timer_" + methodName
    }
}