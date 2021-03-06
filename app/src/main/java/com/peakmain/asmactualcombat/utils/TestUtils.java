package com.peakmain.asmactualcombat.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * author ：Peakmain
 * createTime：2022/4/20
 * mail:2726449200@qq.com
 * describe：
 */
public class TestUtils {
    public static String getDeviceId(Context context) {
        String tac = "";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getDeviceId() == null || manager.getDeviceId().equals("")) {
            if (Build.VERSION.SDK_INT >= 23) {
                tac = manager.getDeviceId(0);
            }
        } else {
            tac = manager.getDeviceId();
        }
        return tac;
    }

    public static String getMeid(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return manager.getMeid();
        }
        return "getMeid";
    }

    /**
     * Android  6.0 之前（不包括6.0）
     */
    public static String getMacDefault(Context context) {
        String mac = "未获取到设备Mac地址";
        if (context == null) return mac;
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        if (info == null) return mac;
        mac = info.getMacAddress();

        if (!TextUtils.isEmpty(mac)) mac = mac.toUpperCase(Locale.ENGLISH);
*/

        return mac;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
