package com.peakmain.sdk.utils;

import android.util.Log;

import androidx.annotation.Keep;

import com.peakmain.sdk.BuildConfig;
import com.peakmain.sdk.SensorsDataAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author ：Peakmain
 * createTime：2022/3/28
 * mail:2726449200@qq.com
 * describe：
 */
public class LogManager {
    private static final String TAG = SensorsDataAPI.class.getCanonicalName();
    private static final DateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Keep
    public static void e(String value) {
        if (!BuildConfig.DEBUG)
            return;
        Log.e(TAG, value);
    }

    @Keep
    public static void d(String value) {
        if (!BuildConfig.DEBUG)
            return;
        Log.d(TAG, value);
    }

    public static void println(boolean value) {
        e(value + "");
    }

    public static void println(byte value) {
        e(value + "");
    }

    public static void println(char value) {
        e(value + "");
    }

    public static void println(short value) {
        e(value + "");
    }

    public static void println(int value) {
        e(String.valueOf(value));
    }

    public static void println(float value) {
        e(value + "");
    }

    public static void println(long value) {
        e(value + "");
    }

    public static void println(double value) {
        e(value + "");
    }

    public static void println(Object value) {
        if (value == null) {
            d("");
        } else if (value instanceof String) {
            d(value + "");
        } else if (value instanceof Date) {
            d(fm.format(value) + "");
        } else if (value instanceof char[]) {
            d(Arrays.toString((char[]) value) + "");
        } else {
            d(value.getClass() + ":" + value.toString());
        }
    }

    public static void printlnStr(String value) {
        e(value);
    }
}
