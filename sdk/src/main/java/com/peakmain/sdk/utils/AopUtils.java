package com.peakmain.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import com.peakmain.sdk.R;

/**
 * author ：Peakmain
 * createTime：2022/7/23
 * mail:2726449200@qq.com
 * describe：
 */
public class AopUtils {
    /**
     * 获取点击 view 的 fragment 对象
     *
     * @param view 点击的 view
     * @return object 这里是 fragment 实例对象
     */
    public static Object getFragmentFromView(View view) {
        return getFragmentFromView(view, null);
    }
    /**
     * 获取点击 view 的 fragment 对象
     *
     * @param view 点击的 view
     * @param activity Activity
     * @return object 这里是 fragment 实例对象
     */
    public static Object getFragmentFromView(View view, Activity activity) {
        try {
            if (view != null) {
                String fragmentName = (String) view.getTag(R.id.sensors_analytics_tag_view_fragment_name);
                if (TextUtils.isEmpty(fragmentName)) {
                    if (activity == null) {
                        //获取所在的 Context
                        Context context = view.getContext();
                        //将 Context 转成 Activity
                        activity = SensorsDataUtils.getActivityFromContext(context);
                    }
                    if (activity != null) {
                        Window window = activity.getWindow();
                        if (window != null && window.isActive()) {
                            Object tag = window.getDecorView().getRootView().getTag(R.id.sensors_analytics_tag_view_fragment_name);
                            if (tag != null) {
                                fragmentName = traverseParentViewTag(view);
                            }
                        }
                    }
                }
                return FragmentCacheUtil.getFragmentFromCache(fragmentName);
            }
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
        return null;
    }

    private static String traverseParentViewTag(View view) {
        try {
            ViewParent parentView = view.getParent();
            String fragmentName = null;
            while (TextUtils.isEmpty(fragmentName) && parentView instanceof View) {
                fragmentName = (String) ((View) parentView).getTag(R.id.sensors_analytics_tag_view_fragment_name);
                parentView = parentView.getParent();
            }
            return fragmentName;
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
        return "";
    }

    public static boolean isValid(int id) {
        return id != -1 && (id & 0xff000000) != 0 && (id & 0x00ff0000) != 0;
    }
}
