package com.peakmain.sdk.manager;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.peakmain.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * author ：Peakmain
 * createTime：2022/3/31
 * mail:2726449200@qq.com
 * describe：
 */
public class SensorsDataManager {
    private static SensorsDatabaseHelper mHelper;
    private final static int DURATION_TIME = 30 * 1000;
    private static WeakReference<Activity> mCurrentActivity;
    private static CountDownTimer mCountDownTimer;

    private static String getToolbarTitle(Activity activity) {
        try {
            android.app.ActionBar actionBar = activity.getActionBar();
            if (actionBar != null) {
                if (!TextUtils.isEmpty(actionBar.getTitle())) {
                    return actionBar.getTitle().toString();
                }
            } else {
                if (activity instanceof AppCompatActivity) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                    ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
                    if (supportActionBar != null) {
                        if (!TextUtils.isEmpty(supportActionBar.getTitle())) {
                            if (supportActionBar.getTitle() != null)
                                return supportActionBar.getTitle().toString();
                            else
                                return "";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 Activity 的 title
     *
     * @param activity Activity
     * @return String 当前页面 title
     */
    private static String getActivityTitle(Activity activity) {
        String activityTitle = null;

        if (activity == null) {
            return null;
        }

        try {
            activityTitle = activity.getTitle().toString();

            String toolbarTitle = getToolbarTitle(activity);
            if (!TextUtils.isEmpty(toolbarTitle)) {
                activityTitle = toolbarTitle;
            }

            if (TextUtils.isEmpty(activityTitle)) {
                PackageManager packageManager = activity.getPackageManager();
                if (packageManager != null) {
                    ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 0);
                    if (activityInfo != null) {
                        activityTitle = activityInfo.loadLabel(packageManager).toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityTitle;
    }

    /**
     * 页面浏览事件
     */
    @Keep
    private static void trackAppViewScreen(Activity activity) {
        try {
            if (activity == null) return;
            JSONObject properties = new JSONObject();
            properties.put("$activity", activity.getClass().getCanonicalName());
            properties.put("title", getActivityTitle(activity));
            SensorsDataAPI.getInstance().track("$AppViewScreen", properties);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Track $AppStart 事件
     */
    private static void trackAppStart(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            JSONObject properties = new JSONObject();
            properties.put("$activity", activity.getClass().getCanonicalName());
            properties.put("$title", getActivityTitle(activity));
            SensorsDataAPI.getInstance().track("$AppStart", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Track $AppEnd 事件
     */
    private static void trackAppEnd(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            JSONObject properties = new JSONObject();
            properties.put("$activity", activity.getClass().getCanonicalName());
            properties.put("$title", getActivityTitle(activity));
            SensorsDataAPI.getInstance().track("$AppEnd", properties);
            mHelper.saveAppEndState(true);
            mCurrentActivity = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void registerActivityLifecycleCallbacks(Application application) {
        mHelper = new SensorsDatabaseHelper(application.getApplicationContext(), application.getPackageName());
        mCountDownTimer = new CountDownTimer(DURATION_TIME, 10 * 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (mCurrentActivity != null) {
                    trackAppEnd(mCurrentActivity.get());
                }
            }
        };
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                mHelper.saveAppStartState(true);
                double timeDiff = System.currentTimeMillis() - mHelper.getAppPausedTime();
                if (timeDiff > DURATION_TIME) {
                    if (!mHelper.getAppEndState()) {
                        trackAppEnd(activity);
                    }
                }
                if (mHelper.getAppEndState()) {
                    mHelper.saveAppEndState(false);
                    trackAppStart(activity);
                }

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                trackAppViewScreen(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                mCurrentActivity = new WeakReference<>(activity);
                mCountDownTimer.start();
                mHelper.saveAppPauseTime(System.currentTimeMillis());
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }
    /**
     * 注册 AppStart 的监听
     */
    public static void registerActivityStateObserver(Application application) {
        application.getContentResolver().registerContentObserver(mHelper.getAppStartUri(),
                false, new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                if(mHelper.getAppStartUri().equals(uri)){
                    if(mCountDownTimer!=null){
                        mCountDownTimer.cancel();
                    }
                }
            }
        });
    }
}
