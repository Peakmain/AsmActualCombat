package com.peakmain.sdk;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.interfaces.OnUploadSensorsDataListener;
import com.peakmain.sdk.manager.SensorsDataManager;
import com.peakmain.sdk.manager.SensorsDatabaseHelper;
import com.peakmain.sdk.utils.SensorsDataUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
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
    private static volatile SensorsDataAPI INSTANCE;
    private static final Object mLock = new Object();
    private static Map<String, Object> mDeviceInfo;
    private String mDeviceId;
    ListenerInfo mListenerInfo;
    private @SensorsDataConstants.STATE
    int mState = SensorsDataConstants.APP_VIEW_CLICK__EVENT_STATE;

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

    public void setOnUploadSensorsDataListener(@Nullable OnUploadSensorsDataListener l) {
        getListenerInfo().mOnUploadSensorsData = l;
    }

    static class ListenerInfo {
        OnUserAgreementListener mOnUserAgreement;
        OnUploadSensorsDataListener mOnUploadSensorsData;
    }

    @Keep
    @SuppressWarnings("UnusedReturnValue")
    public static SensorsDataAPI init(Application application) {
        if (INSTANCE == null) {
            synchronized (mLock) {
                if (null == INSTANCE) {
                    INSTANCE = new SensorsDataAPI(application);
                }
            }
        }
        return INSTANCE;
    }

    @Keep
    public static SensorsDataAPI getInstance() {
        return INSTANCE;
    }

    private SensorsDataAPI(Application application) {
        mDeviceInfo = SensorsDataUtils.getDeviceInfo(application.getApplicationContext());
        SensorsDataManager.registerActivityLifecycleCallbacks(application);
        SensorsDataManager.registerActivityStateObserver(application);
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

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event", eventName);
            //jsonObject.put("device_id", mDeviceId);

            JSONObject sendProperties = new JSONObject(mDeviceInfo);

            if (properties != null) {
                SensorsDataUtils.mergeJSONObject(properties, sendProperties);
            }

            jsonObject.put("properties", sendProperties);
            long currentTimeMillis = System.currentTimeMillis();
            jsonObject.put("time", currentTimeMillis);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonObject.put("currentTime", simpleDateFormat.format(currentTimeMillis));
            //获取到埋点之后，上传到服务器
            OnUploadSensorsDataListener onUploadSensorsData = getListenerInfo().mOnUploadSensorsData;
            if (onUploadSensorsData != null) {
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
                onUploadSensorsData.onUploadSensors(mState, SensorsDataUtils.formatJson(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
