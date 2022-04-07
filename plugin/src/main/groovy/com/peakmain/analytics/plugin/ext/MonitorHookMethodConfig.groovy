package com.peakmain.analytics.plugin.ext

import com.peakmain.analytics.plugin.entity.MethodCalledBean

/**
 * author ：Peakmain
 * createTime：2022/4/2
 * mail:2726449200@qq.com
 * describe：
 */
class MonitorHookMethodConfig {
    public final static HashMap<String, MethodCalledBean> methodCalledBeans = new HashMap<>()
    static {
        String[] device_desc = ["()Ljava/lang/String;", "(I)Ljava/lang/String;"]
        MethodCalledBean deviceIds = new MethodCalledBean(
                "android/telephony/TelephonyManager",
                "getDeviceId",device_desc)
        for (String desc : deviceIds.getDescriptor()) {
            println(deviceIds.className + deviceIds.getName()+desc)
            methodCalledBeans.put(deviceIds.className + deviceIds.getName() + desc, deviceIds)
        }

    }

}