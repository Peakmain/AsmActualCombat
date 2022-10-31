package com.peakmain.sdk;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.interfaces.ISensorsDataInstance;
import com.peakmain.sdk.interfaces.OnReplaceMethodListener;
import com.peakmain.sdk.interfaces.OnUploadSensorsDataListener;
import com.peakmain.sdk.manager.SensorsDataManager;
import com.peakmain.sdk.manager.SensorsDatabaseHelper;
import com.peakmain.sdk.utils.PreferencesUtil;
import com.peakmain.sdk.utils.ReplaceMethodUtils;
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
    private static volatile SensorsDataAPI INSTANCE;
    private static final Object mLock = new Object();
    ListenerInfo mListenerInfo;


    private final ISensorsDataInstance iSensorsDataInstance;

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

    @Keep
    public void onEvent(String eventName, String eventValue) {
        iSensorsDataInstance.onEvent(eventName, eventValue);
    }

    /**
     * 设置方法替换接口
     *
     * @param onReplaceMethodListener onReplaceMethodListener
     */
    public void setOnReplaceMethodListener(OnReplaceMethodListener onReplaceMethodListener) {
        ReplaceMethodUtils.getInstance().setOnReplaceMethodListener(onReplaceMethodListener);
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


    public void track(String eventName) {
        track(eventName, new JSONObject());
    }
    private SensorsDataAPI(Application application) {
        iSensorsDataInstance = new SensorsDataInstance(application);
        SensorsDataManager.registerActivityLifecycleCallbacks(application);
        SensorsDataManager.registerActivityStateObserver(application);
        PreferencesUtil.getInstance().init(application);
    }

    /**
     * Track 事件
     *
     * @param eventName  String 事件名称
     * @param properties JSONObject 事件属性
     */
    @Keep
    public void track(@NonNull final String eventName, @Nullable JSONObject properties) {
        iSensorsDataInstance.track(eventName, properties, getListenerInfo().mOnUploadSensorsData);
    }

}
