package com.peakmain.asmactualcombat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.ui.utils.LogUtils;

import java.util.List;
import java.util.Locale;

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
public class Utils {
    Context mContext;
    WifiManager mWifiManager;

    public Utils(Context context, WifiManager wifiManager) {
        this.mContext = context;
        this.mWifiManager = wifiManager;
    }

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

    public static String getSubscriberId(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return manager.getSubscriberId();
        }
        return "getSubscriberId";
    }

    public static String getImei(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return manager.getImei();
        }
        return "getImei";
    }

    public static String getSimSerialNumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return manager.getSimSerialNumber();
        }
        return "getSimSerialNumber";
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

    public final WifiInfo getConnectionInfo() {
        try {
            try {
                return mWifiManager.getConnectionInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable var1) {
        }

        return null;
    }

    public final WifiInfo getConnectionInfo1() {
        try {
            try {
                if (mWifiManager.getConnectionInfo() != null) {
                    LogUtils.e("not null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable var1) {
        }

        return null;
    }

    public final boolean a(ConnectivityManager var1) {
        WifiManager var2;
        if ((var2 = this.mWifiManager) == null) {
            return false;
        } else {
            boolean var3 = false;

            try {
                if (var2.getConnectionInfo() != null) {
                    var3 = true;
                }
            } catch (Throwable var4) {
            }

            return var3;
        }
    }

    public static int getIpAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info.getIpAddress();
    }

    public static String getBSSID(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info.getBSSID();
    }

    public static String getSSID(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info.getSSID();
    }

    public List<ScanResult> getScanResults() {
        if (this.mWifiManager != null) {
            List<ScanResult> var1 = this.mWifiManager.getScanResults();
            for (ScanResult scanResult : var1) {
                LogUtils.e(scanResult.BSSID);
            }
            return var1;
        }
        return null;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
