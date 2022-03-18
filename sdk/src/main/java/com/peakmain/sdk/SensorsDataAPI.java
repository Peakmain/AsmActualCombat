package com.peakmain.sdk;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.peakmain.sdk.utils.SensorsDataUtils;

import org.json.JSONObject;

import java.util.Map;

/**
 * author:Peakmain
 * createTime:2021/6/15
 * mail:2726449200@qq.com
 * describe：
 */
@Keep
public class SensorsDataAPI {
    private final String TAG = this.getClass().getSimpleName();
    public static final String SDK_VERSION = "1.0.0";
    private static SensorsDataAPI INSTANCE;
    private static final Object mLock = new Object();
    private static Map<String, Object> mDeviceInfo;
    private String mDeviceId;
    ListenerInfo mListenerInfo;
    private Application mApplication;

    ListenerInfo getListenerInfo() {
        if (mListenerInfo != null) {
            return mListenerInfo;
        }
        mListenerInfo = new ListenerInfo();
        return mListenerInfo;
    }


    public interface OnUserAgreementListener {
        /**
         * 是否同意用户协议接口
         */
        boolean onUserAgreement();
    }

    public void setOnUserAgreementListener(@Nullable OnUserAgreementListener l) {
        getListenerInfo().mOnUserAgreement = l;
    }

    static class ListenerInfo {
        OnUserAgreementListener mOnUserAgreement;
    }

    @Keep
    @SuppressWarnings("UnusedReturnValue")
    public static SensorsDataAPI init(Application application) {
        synchronized (mLock) {
            if (null == INSTANCE) {
                INSTANCE = new SensorsDataAPI(application);
            }
        }
        return INSTANCE;
    }

    @Keep
    public static SensorsDataAPI getInstance() {
        return INSTANCE;
    }

    private SensorsDataAPI(Application application) {
        mApplication = application;
    }

    /**
     * Track 事件
     *
     * @param eventName  String 事件名称
     * @param properties JSONObject 事件属性
     */
    @Keep
    public void track(@NonNull final String eventName, @Nullable JSONObject properties) {
        try {
           // mDeviceId = SensorsDataUtils.getAndroidID(mApplication.getApplicationContext());
            mDeviceInfo = SensorsDataUtils.getDeviceInfo(mApplication.getApplicationContext());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event", eventName);
            //jsonObject.put("device_id", mDeviceId);

            JSONObject sendProperties = new JSONObject(mDeviceInfo);

            if (properties != null) {
                SensorsDataUtils.mergeJSONObject(properties, sendProperties);
            }

            jsonObject.put("properties", sendProperties);
            jsonObject.put("time", System.currentTimeMillis());

            if (BuildConfig.DEBUG) {
                Log.i(TAG, SensorsDataUtils.formatJson(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
