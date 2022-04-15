package com.peakmain.sdk.utils;

import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.interfaces.OnReplaceMethodListener;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

/**
 * author ：Peakmain
 * createTime：2022/4/2
 * mail:2726449200@qq.com
 * describe：替换方法
 */
public class ReplaceMethodUtils {
    private OnReplaceMethodListener mOnReplaceMethodListener;

    private ReplaceMethodUtils() {

    }

    public static ReplaceMethodUtils getInstance() {
        return Holder.instance;
    }

    public void setOnReplaceMethodListener(OnReplaceMethodListener onReplaceMethodListener) {
        mOnReplaceMethodListener = onReplaceMethodListener;
    }

    private static class Holder {
        private static final ReplaceMethodUtils instance = new ReplaceMethodUtils();
    }

    public static String getDeviceId(TelephonyManager manager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_DEVICE_ID, manager, -1000);
        }
        return "";
    }

    public static String getDeviceId(TelephonyManager manager, int slotIndex) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_DEVICE_ID, manager, slotIndex);
        }
        return "";
    }


    public static String getMeid(TelephonyManager manager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_MEID, manager, -1000);
        }
        return "";
    }

    public static String getMeid(TelephonyManager manager, int slotIndex) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_MEID, manager, slotIndex);
        }
        return "";
    }

    public static String getImei(TelephonyManager manager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_IMEI, manager, -1000);
        }
        return "";
    }

    /**
     * 获取Imei
     */
    public static String getImei(TelephonyManager manager, int slotIndex) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_IMEI, manager, slotIndex);
        }
        return "";
    }

    /**
     * 获取IMSI号
     */
    public static String getSubscriberId(TelephonyManager manager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_SUBSCRIBER_ID, manager, -1000);
        }
        return "";
    }

    public static String getSimSerialNumber(TelephonyManager manager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_SIM_SERIAL_NUMBER, manager, -1000);
        }
        return "";
    }

    public static String getSimSerialNumber(TelephonyManager manager, int subId) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_SUBSCRIBER_ID, manager, subId);
        }
        return "";
    }

    public static String getMacAddress(WifiInfo wifiInfo) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(SensorsDataConstants.GET_MAC_ADDRESS, wifiInfo);
        }
        return "";
    }

    public static String getSSID(WifiInfo wifiInfo) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_SSID, wifiInfo);
        }
        return "";
    }

    public static String getBSSID(WifiInfo wifiInfo) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_BSSID, wifiInfo);
        }
        return "";
    }

    public static int getIpAddress(WifiInfo wifiInfo) {
        if (getInstance().mOnReplaceMethodListener != null) {
            String ipAddressStr = getInstance().mOnReplaceMethodListener.onReplaceMethodListener(
                    SensorsDataConstants.GET_SSID, wifiInfo);
            return !TextUtils.isEmpty(ipAddressStr) ? Integer.parseInt(ipAddressStr) : -1;
        }
        return -1;
    }

    public static WifiInfo getConnectionInfo(WifiManager wifiManager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(wifiManager);
        }
        return null;
    }


    public static String getIccId(SubscriptionInfo subscriptionInfo) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(subscriptionInfo);
        }
        return "";
    }

    public static List<PackageInfo> getInstalledPackages(PackageManager manager) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(manager);
        }
        return new ArrayList<>();
    }


    public static String getString(ContentResolver resolver, String name) {
        if (getInstance().mOnReplaceMethodListener != null) {
            return getInstance().mOnReplaceMethodListener.onReplaceMethodListener(resolver, name);
        }
        return "";
    }

}
