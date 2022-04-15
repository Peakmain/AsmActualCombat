package com.peakmain.sdk.interfaces;

import android.content.ContentResolver;
import android.telephony.TelephonyManager;

/**
 * author ：Peakmain
 * createTime：2022/4/15
 * mail:2726449200@qq.com
 * describe：替换方法监听事件
 */
public interface OnReplaceMethodListener {
    String onReplaceMethodListener(TelephonyManager manager);

    String onReplaceMethodListener(TelephonyManager manager, int slotIndex);

    String getString(ContentResolver resolver, String name);

}
