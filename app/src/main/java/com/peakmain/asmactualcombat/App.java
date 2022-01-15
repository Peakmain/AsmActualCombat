package com.peakmain.asmactualcombat;

import android.app.Application;

import com.peakmain.sdk.SensorsDataAPI;

/**
 * author ：Peakmain
 * createTime：2021/6/15
 * describe：
 */
public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SensorsDataAPI.init(this);
        SensorsDataAPI.getInstance().setOnUserAgreementListener(() -> true);
    }
}
