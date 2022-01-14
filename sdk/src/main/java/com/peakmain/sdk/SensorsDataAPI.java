package com.peakmain.sdk;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @Keep
    @SuppressWarnings("UnusedReturnValue")
    public static SensorsDataAPI init(Application application) {
        synchronized (mLock) {
            if (null == INSTANCE) {
                INSTANCE = new SensorsDataAPI(application);
            }
            return INSTANCE;
        }
    }

    @Keep
    public static SensorsDataAPI getInstance() {
        return INSTANCE;
    }

    private SensorsDataAPI(Application application) {
        mDeviceId = SensorsDataPrivate.getAndroidID(application.getApplicationContext());
        mDeviceInfo = SensorsDataPrivate.getDeviceInfo(application.getApplicationContext());
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event", eventName);
            jsonObject.put("device_id", mDeviceId);

            JSONObject sendProperties = new JSONObject(mDeviceInfo);

            if (properties != null) {
                SensorsDataPrivate.mergeJSONObject(properties, sendProperties);
            }

            jsonObject.put("properties", sendProperties);
            jsonObject.put("time", System.currentTimeMillis());

            if(BuildConfig.DEBUG){
                Log.i(TAG, SensorsDataPrivate.formatJson(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
