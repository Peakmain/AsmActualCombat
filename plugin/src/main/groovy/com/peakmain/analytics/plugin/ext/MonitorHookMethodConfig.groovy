package com.peakmain.analytics.plugin.ext

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.utils.MethodFieldUtils

/**
 * author ：Peakmain
 * createTime：2022/4/2
 * mail:2726449200@qq.com
 * describe：
 */
class MonitorHookMethodConfig {
    public final static HashMap<String, MethodCalledBean> methodCalledBeans = new HashMap<>()
    static {
        /**
         * 获取设备id
         */
        MethodCalledBean deviceIds = new MethodCalledBean(
                MethodFieldUtils.TELEPHONY_MANAGER_CLASS,
                MethodFieldUtils.GET_DEVICE_ID_METHOD_NAME,
                MethodFieldUtils.EMPTY_INT_RETURN_STRING_DESC)
        addMethodCalledBean(deviceIds)
        /**
         *  获取getMeid
         */
        MethodCalledBean meIds = new MethodCalledBean(
                MethodFieldUtils.TELEPHONY_MANAGER_CLASS,
                MethodFieldUtils.GET_MEID_METHOD_NAME,
                MethodFieldUtils.EMPTY_INT_RETURN_STRING_DESC)
        addMethodCalledBean(meIds)
        /**
         * 获取connectionInfo
         */
        MethodCalledBean connectionInfo = new MethodCalledBean(
                MethodFieldUtils.WIFI_MANAGER_CLASS,
                MethodFieldUtils.GET_CONNECTION_INFO,
                MethodFieldUtils.EMPTY_RETURN_WIFI_INFO_DESC
        )
        addMethodCalledBean(connectionInfo)
        /**
         * 获取Mac
         */
        MethodCalledBean macAddress = new MethodCalledBean(
                MethodFieldUtils.WIFI_INFO_CLASS,
                MethodFieldUtils.GET_MAC_ADDRESS,
                MethodFieldUtils.EMPTY_RETURN_STRING_DESC
        )
        addMethodCalledBean(macAddress)
    }

    static void addMethodCalledBean(MethodCalledBean methodCalledBean) {
        for (String desc : methodCalledBean.getDescriptor()) {
            methodCalledBeans.put(methodCalledBean.className + methodCalledBean.getName() + desc, methodCalledBean)
        }
    }

}