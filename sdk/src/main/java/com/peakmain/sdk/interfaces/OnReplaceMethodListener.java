package com.peakmain.sdk.interfaces;

import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;

import com.peakmain.sdk.constants.SensorsDataConstants;

import java.util.List;

/**
 * author ：Peakmain
 * createTime：2022/4/15
 * mail:2726449200@qq.com
 * describe：替换方法监听事件
 */
public interface OnReplaceMethodListener {
    String onReplaceMethodListener(@SensorsDataConstants.TELEPHONY_STATE int telephoneState, TelephonyManager manager, int slotIndex);

    String onReplaceMethodListener(@SensorsDataConstants.WIFI_STATE int wifiInfoState, WifiInfo wifiInfo);

    WifiInfo onReplaceMethodListener(WifiManager wifiManager);

    String onReplaceMethodListener(SubscriptionInfo subscriptionInfo);

    List<PackageInfo> onReplaceMethodListener(PackageManager manager);

    String onReplaceMethodListener(ContentResolver resolver, String name);

}
