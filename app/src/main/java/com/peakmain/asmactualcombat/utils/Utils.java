package com.peakmain.asmactualcombat.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
public class Utils {
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

    /**
     * Android  6.0 之前（不包括6.0）
     *
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
        if (info == null) return mac;
        mac = info.getMacAddress();

        if (!TextUtils.isEmpty(mac)) mac = mac.toUpperCase(Locale.ENGLISH);

        return mac;
    }
}
