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
        HashMap<String, String> deviceIdMap = new HashMap<>()
        deviceIdMap.put(MethodFieldUtils.EMPTY_STRING_DESC, MethodFieldUtils.NEW_EMPTY_STRING_DESC)
        deviceIdMap.put(MethodFieldUtils.INT_STRING_DESC, MethodFieldUtils.NEW_INT_STRING_DESC)
        MethodCalledBean deviceIds = new MethodCalledBean(
                MethodFieldUtils.TELEPHONY_MANAGER_CLASS,
                MethodFieldUtils.GET_DEVICE_ID_METHOD_NAME,
                MethodFieldUtils.EMPTY_INT_RETURN_STRING_DESC,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_DEVICE_ID_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                deviceIdMap)
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
                MethodFieldUtils.GET_CONNECTION_INFO_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_WIFI_INFO_DESC
        )
        addMethodCalledBean(connectionInfo)
        /**
         * 获取Mac
         */
        MethodCalledBean macAddress = new MethodCalledBean(
                MethodFieldUtils.WIFI_INFO_CLASS,
                MethodFieldUtils.GET_MAC_ADDRESS_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_STRING_DESC
        )
        addMethodCalledBean(macAddress)
        /**
         * IP地址
         */
        MethodCalledBean ipAddress = new MethodCalledBean(
                MethodFieldUtils.WIFI_INFO_CLASS,
                MethodFieldUtils.GET_IP_ADDRESS_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_INT_DESC
        )
        addMethodCalledBean(ipAddress)
        /**
         * 获取AndroidId
         */
        MethodCalledBean androidId = new MethodCalledBean(
                MethodFieldUtils.SETTINGS_SECURE_CLASS,
                MethodFieldUtils.GET_STRING_METHOD_NAME,
                MethodFieldUtils.CONTENTRESOLVER_STRING_RETURN_STRING_DESC
        )
        addMethodCalledBean(androidId)
    }

    static void addMethodCalledBean(MethodCalledBean methodCalledBean) {
        for (String desc : methodCalledBean.getMethodDescriptor()) {
            methodCalledBeans.put(methodCalledBean.getMethodOwner() + methodCalledBean.getMethodName() + desc, methodCalledBean)
        }
    }

}