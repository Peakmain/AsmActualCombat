package com.peakmain.sdk.utils;

import android.text.TextUtils;

import androidx.collection.LruCache;

import java.lang.ref.WeakReference;

/**
 * author ：Peakmain
 * createTime：2022/7/23
 * mail:2726449200@qq.com
 * describe：
 */
public class FragmentCacheUtil {
    private static LruCache<String, WeakReference<Object>> sFragmentLruCache = new LruCache<>(Integer.MAX_VALUE);
    public static void setFragmentToCache(String fragmentName, Object object) {
        if (!TextUtils.isEmpty(fragmentName) && null != object) {
            sFragmentLruCache.put(fragmentName, new WeakReference<>(object));
        }
    }

    public static Object getFragmentFromCache(String fragmentName) {
        try {
            if (!TextUtils.isEmpty(fragmentName)) {
                WeakReference<Object> weakReference = null;
                weakReference = sFragmentLruCache.get(fragmentName);
                Object object;
                if (null != weakReference) {
                    object = weakReference.get();
                    if (null != object) {
                        return object;
                    }
                }
                object = Class.forName(fragmentName).newInstance();
                sFragmentLruCache.put(fragmentName, new WeakReference<>(object));
                return object;
            }
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
        return null;
    }
}
