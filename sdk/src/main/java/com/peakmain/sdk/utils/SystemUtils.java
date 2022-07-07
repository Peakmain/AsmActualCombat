package com.peakmain.sdk.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * author ：Peakmain
 * createTime：2022/7/7
 * mail:2726449200@qq.com
 * describe：
 */
public class SystemUtils {
    public static long getVersionCode(Context context) {
        long versionCode = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode();
            }else{
                versionCode=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    public static String getVersionName(Context context){
        String versionName = null;
        try {
            versionName=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();
        } else {
            return Resources.getSystem().getConfiguration().locale.getLanguage();
        }
    }

}
