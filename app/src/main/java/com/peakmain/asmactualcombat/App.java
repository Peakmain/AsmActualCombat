package com.peakmain.asmactualcombat;

import android.app.Application;
import android.util.Log;

import com.peakmain.sdk.BuildConfig;
import com.peakmain.sdk.SensorsDataAPI;
import com.peakmain.sdk.interfaces.OnUploadSensorsDataListener;
import com.peakmain.sdk.utils.SensorsDataUtils;

/**
 * author ：Peakmain
 * createTime：2021/6/15
 * describe：
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SensorsDataAPI.init(this);
        SensorsDataAPI.getInstance().setOnUserAgreementListener(() -> true);
        SensorsDataAPI.getInstance().setOnUploadSensorsDataListener(new OnUploadSensorsDataListener() {
            @Override
            public void onUploadSensors(String data) {
                if (BuildConfig.DEBUG) {
                    Log.e("TAG", data);
                }
            }
        });
    }
}
