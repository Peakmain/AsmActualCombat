package com.peakmain.asmactualcombat;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.peakmain.sdk.BuildConfig;
import com.peakmain.sdk.SensorsDataAPI;
import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.interfaces.OnReplaceMethodListener;
import com.peakmain.sdk.interfaces.OnUploadSensorsDataListener;
import com.peakmain.sdk.utils.SensorsDataUtils;
import com.peakmain.ui.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

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
                        Log.e("TAG", "埋点\n" + data);
                    }
                    break;
                default:
                    if (BuildConfig.DEBUG) {
                        Log.e("TAG", data);
                    }
                    break;
            }
        });
        SensorsDataAPI.getInstance().setOnReplaceMethodListener(new OnReplaceMethodListener() {
            @Override
            public String onReplaceMethodListener(int telephoneState, TelephonyManager manager, int slotIndex) {
                switch (telephoneState) {
                    case SensorsDataConstants.GET_DEVICE_ID:
                        LogUtils.e("替换GET_DEVICE_ID");
                        break;
                    case SensorsDataConstants.GET_MEID:
                        LogUtils.e("替换GET_MEID");
                        break;
                    case SensorsDataConstants.GET_IMEI:
                        LogUtils.e("替换GET_IMEI");
                        break;
                    case SensorsDataConstants.GET_SUBSCRIBER_ID:
                        LogUtils.e("替换GET_SUBSCRIBER_ID");
                        break;
                    case SensorsDataConstants.GET_SIM_SERIAL_NUMBER:
                        LogUtils.e("替换GET_SIM_SERIAL_NUMBER");
                        break;
                    default:
                        break;
                }
                return "";
            }

            @Override
            public String onReplaceMethodListener(int wifiInfoState, WifiInfo wifiInfo) {
                switch (wifiInfoState) {
                    case SensorsDataConstants.GET_MAC_ADDRESS:
                        LogUtils.e("替换GET_MAC_ADDRESS");
                        break;
                    case SensorsDataConstants.GET_SSID:
                        LogUtils.e("替换GET_SSID");
                        break;
                    case SensorsDataConstants.GET_BSSID:
                        LogUtils.e("替换GET_SSIDGET_BSSID");
                        break;
                    case SensorsDataConstants.GET_IP_ADDRESS:
                        LogUtils.e("替换GET_IP_ADDRESS");
                        break;
                    default:
                        break;
                }
                return "";
            }

            @Override
            public WifiInfo onReplaceMethodListener(WifiManager wifiManager) {
                LogUtils.e("替换WifiManager");
                return null;
            }

            @Override
            public String onReplaceMethodListener(SubscriptionInfo subscriptionInfo) {
                LogUtils.e("替换SubscriptionInfo");
                return "";
            }

            @Override
            public List<PackageInfo> onReplaceMethodListener(PackageManager manager) {
                LogUtils.e("替换PackageManager");
                return new ArrayList<>();
            }

            @Override
            public String onReplaceMethodListener(ContentResolver resolver, String name) {
                LogUtils.e("替换ContentResolver");
                return "onReplaceMethodListener";
            }
        });
    }
}
