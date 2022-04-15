package com.peakmain.sdk.constants;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
public class SensorsDataConstants {
    public static final String APP_START_EVENT_NAME = "$AppStart";
    public static final String APP_END__EVENT_NAME = "$AppEnd";
    public static final String APP_VIEW_SCREEN__EVENT_NAME = "$AppViewScreen";
    public static final String APP_VIEW_CLICK__EVENT_NAME = "$AppClick";

    public static final int APP_START_EVENT_STATE = 1;
    public static final int APP_END__EVENT_STATE = 2;
    public static final int APP_VIEW_SCREEN__EVENT_STATE = 3;
    public static final int APP_VIEW_CLICK__EVENT_STATE = 4;

    @IntDef({APP_START_EVENT_STATE, APP_VIEW_SCREEN__EVENT_STATE, APP_END__EVENT_STATE, APP_VIEW_CLICK__EVENT_STATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE{}

    public static final int GET_DEVICE_ID = 1;
    public static final int GET_MEID = 2;
    public static final int GET_IMEI = 3;
    public static final int GET_SUBSCRIBER_ID = 4;
    public static final int GET_SIM_SERIAL_NUMBER = 5;
    public static final int GET_MAC_ADDRESS = 6;
    public static final int GET_SSID = 7;
    public static final int GET_CONNECTION_INFO = 8;
    public static final int GET_BSSID = 9;

}
