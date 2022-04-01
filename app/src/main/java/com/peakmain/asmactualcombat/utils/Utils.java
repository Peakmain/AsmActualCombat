package com.peakmain.asmactualcombat.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
public class Utils {
    public static String getDeviceId(Context context) {
        String tac="";
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
}
