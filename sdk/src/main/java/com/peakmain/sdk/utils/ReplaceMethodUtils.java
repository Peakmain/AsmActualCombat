package com.peakmain.sdk.utils;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * author ：Peakmain
 * createTime：2022/4/2
 * mail:2726449200@qq.com
 * describe：替换方法
 */
public class ReplaceMethodUtils {

    public static String getDeviceId(TelephonyManager manager) {
        return "";
    }

    public static String getDeviceId(TelephonyManager manager, int slotIndex) {
        return "";
    }

    public String getImei(TelephonyManager manager) {
        return "";
    }

    public static String getImei(TelephonyManager manager, int slotIndex) {
        return "";
    }

    public static String getMacAddress(WifiInfo wifiInfo) {
        return "";
    }
    public static String getSSID(WifiInfo wifiInfo) {
        return "";
    }
    public static WifiInfo getConnectionInfo(WifiManager wifiManager){
        return null;
    }

}
