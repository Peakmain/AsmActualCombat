package com.peakmain.sdk.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

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

    public String getMeid(TelephonyManager manager) {
        return "";
    }

    /**
     * 获取Imei
     */
    public static String getImei(TelephonyManager manager, int slotIndex) {
        return "";
    }

    /**
     * 获取IMSI号
     */
    public static String getSubscriberId(TelephonyManager manager) {
        return "";
    }

    public static String getSimSerialNumber(TelephonyManager manager) {
        return "";
    }

    public String getSimSerialNumber(TelephonyManager manager, int subId) {
        return "";
    }

    public static String getMacAddress(WifiInfo wifiInfo) {
        return "";
    }

    public static String getSSID(WifiInfo wifiInfo) {
        return "";
    }

    public static WifiInfo getConnectionInfo(WifiManager wifiManager) {
        return null;
    }

    public static String getBSSID(WifiInfo wifiInfo) {
        return "";
    }

    public String getIccId(SubscriptionInfo subscriptionInfo) {
        return "";
    }

    public List<PackageInfo> getInstalledPackages(PackageManager manager) {
        return new ArrayList<>();
    }

}
