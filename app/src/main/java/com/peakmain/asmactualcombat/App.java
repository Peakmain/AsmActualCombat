package com.peakmain.asmactualcombat;

import android.app.Application;
import android.util.Log;

import com.peakmain.sdk.BuildConfig;
import com.peakmain.sdk.SensorsDataAPI;
import com.peakmain.sdk.constants.SensorsDataConstants;
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
