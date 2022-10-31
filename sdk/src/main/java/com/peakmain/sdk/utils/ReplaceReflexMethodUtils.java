package com.peakmain.sdk.utils;

import android.util.Log;

import com.peakmain.sdk.map.p;

import java.lang.reflect.Method;

/**
 * author ：Peakmain
 * createTime：2022/6/10
 * mail:2726449200@qq.com
 * describe：
 */
public class ReplaceReflexMethodUtils {

    public static Method a(Class clazz, String name, Class<?>... var2)  {
        if("getImei".equals(name)){
            Log.e("TAG","getImei");
            return null;
        }
        try {
            return clazz.getDeclaredMethod(c(name), var2);
        } catch (Throwable var3) {
            return null;
        }
    }
    public static String c(String var0) {
        return var0.length() < 2 ? "" : p.a(var0.substring(1));
    }
    public static Object a(String var0, String name, Object[] var2, Class<?>[] var3) throws Exception {
        if("getImei".equals(name)){
            Log.e("TAG","getImei");
            return null;
        }
        return a(Class.forName(var0), name, var2, var3);
    }
    private static Object a(Class<?> var0, String var1, Object[] var2, Class<?>[] var3) throws Exception {
        Method var4;
        if (!(var4 = var0.getDeclaredMethod(var1, var3)).isAccessible()) {
            var4.setAccessible(true);
        }

        return var4.invoke((Object)null, var2);
    }

}
