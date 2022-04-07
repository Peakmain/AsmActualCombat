package com.peakmain.asmactualcombat;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.peakmain.sdk.BuildConfig;
import com.peakmain.sdk.SensorsDataAPI;
import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.interfaces.OnUploadSensorsDataListener;
import com.peakmain.sdk.utils.SensorsDataUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * author ：Peakmain
 * createTime：2021/6/15
 * describe：
 */
public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SensorsDataAPI.init(this);
        SensorsDataAPI.getInstance().setOnUserAgreementListener(() -> true);
        SensorsDataAPI.getInstance().setOnUploadSensorsDataListener((state, data) -> {
            switch (state) {
                case SensorsDataConstants.APP_START_EVENT_STATE:
                case SensorsDataConstants.APP_END__EVENT_STATE:
                case SensorsDataConstants.APP_VIEW_SCREEN__EVENT_STATE:
                    if (BuildConfig.DEBUG) {
                        Log.e("TAG", "埋点\n"+data);
                    }
                    break;
                default:
                    if (BuildConfig.DEBUG) {
                        Log.e("TAG", data);
                    }
                    break;
            }
        });
    }
}
