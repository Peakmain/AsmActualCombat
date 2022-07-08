package com.peakmain.sdk.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.peakmain.sdk.SensorsDataAPI;

import org.json.JSONObject;

/**
 * author ：Peakmain
 * createTime：2022/7/8
 * mail:2726449200@qq.com
 * describe：
 */
public interface ISensorsDataInstance {


    void onEvent(String eventName, String eventValue);
    void track(@NonNull final String eventName, @Nullable JSONObject properties);
    void track(@NonNull final String eventName, @Nullable JSONObject properties,@Nullable OnUploadSensorsDataListener onUploadSensorsDataListener);

}
