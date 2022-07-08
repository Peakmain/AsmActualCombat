package com.peakmain.sdk;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.interfaces.ISensorsDataInstance;
import com.peakmain.sdk.interfaces.OnUploadSensorsDataListener;
import com.peakmain.sdk.utils.SensorsDataUtils;
import com.qiniu.android.utils.Json;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * author ：Peakmain
 * createTime：2022/7/8
 * mail:2726449200@qq.com
 * describe：
 */
public class SensorsDataInstance implements ISensorsDataInstance {

    private @SensorsDataConstants.STATE
    int mState = SensorsDataConstants.APP_VIEW_CLICK__EVENT_STATE;

    private static Map<String, Object> mDeviceInfo;

    public SensorsDataInstance(Application application) {
        mDeviceInfo = SensorsDataUtils.getDeviceInfo(application.getApplicationContext());
    }

    @Override
    public void onEvent(String eventName, String eventValue) {
    }

    @Override
    public void track(@NonNull String eventName, @Nullable JSONObject properties) {
        track(eventName, properties, null);
    }

    @Override
    public void track(@NonNull String eventName, @Nullable JSONObject properties, OnUploadSensorsDataListener onUploadSensorsDataListener) {
        track(eventName, properties, null, onUploadSensorsDataListener);
    }

    public void track(@NonNull String eventName, @Nullable JSONObject properties, @Nullable JSONObject customProperties, OnUploadSensorsDataListener onUploadSensorsDataListener) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event", eventName);

            JSONObject sendProperties = new JSONObject(mDeviceInfo);

            if (properties != null) {
                SensorsDataUtils.mergeJSONObject(properties, sendProperties);
            }
            if (customProperties != null) {
                SensorsDataUtils.mergeJSONObject(customProperties, sendProperties);
            }
            jsonObject.put("params", sendProperties);
            long currentTimeMillis = System.currentTimeMillis();
            jsonObject.put("time", currentTimeMillis);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonObject.put("event_date", simpleDateFormat.format(currentTimeMillis));
            //获取到埋点之后，上传到服务器
            if (onUploadSensorsDataListener != null) {
                switch (eventName) {
                    case SensorsDataConstants.APP_START_EVENT_NAME:
                        mState = SensorsDataConstants.APP_START_EVENT_STATE;
                        break;
                    case SensorsDataConstants.APP_END__EVENT_NAME:
                        mState = SensorsDataConstants.APP_END__EVENT_STATE;
                        break;
                    case SensorsDataConstants.APP_VIEW_SCREEN__EVENT_NAME:
                        mState = SensorsDataConstants.APP_VIEW_SCREEN__EVENT_STATE;
                        break;
                    default:
                        mState = SensorsDataConstants.APP_VIEW_CLICK__EVENT_STATE;
                        break;
                }
                onUploadSensorsDataListener.onUploadSensors(mState, SensorsDataUtils.formatJson(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
