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
        deviceIdMap.put(MethodFieldUtils.EMPTY_STRING_DESC, MethodFieldUtils.NEW_EMPTY_STRING_TELEPHONY_DESC)
        deviceIdMap.put(MethodFieldUtils.INT_STRING_DESC, MethodFieldUtils.NEW_INT_STRING_TELEPHONY_DESC)
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
        HashMap<String, String> meIdMap = new HashMap<>()
        meIdMap.put(MethodFieldUtils.EMPTY_STRING_DESC, MethodFieldUtils.NEW_EMPTY_STRING_TELEPHONY_DESC)
        meIdMap.put(MethodFieldUtils.INT_STRING_DESC, MethodFieldUtils.NEW_INT_STRING_TELEPHONY_DESC)
        MethodCalledBean meIds = new MethodCalledBean(
                MethodFieldUtils.TELEPHONY_MANAGER_CLASS,
                MethodFieldUtils.GET_MEID_METHOD_NAME,
                MethodFieldUtils.EMPTY_INT_RETURN_STRING_DESC,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_MEID_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                meIdMap
        )
        addMethodCalledBean(meIds)
        /**
         * 获取connectionInfo
         */
        HashMap<String, String> connectionInfoMap = new HashMap<>()
        connectionInfoMap.put(MethodFieldUtils.EMPTY_WIFI_INFO_DESC, MethodFieldUtils.NEW_WIFI_INFO_WIFI_INFO_DESC)
        MethodCalledBean connectionInfo = new MethodCalledBean(
                MethodFieldUtils.WIFI_MANAGER_CLASS,
                MethodFieldUtils.GET_CONNECTION_INFO_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_WIFI_INFO_DESC,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_CONNECTION_INFO_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                connectionInfoMap
        )
        addMethodCalledBean(connectionInfo)
        /**
         * 获取Mac
         */
        HashMap<String, String> macAddressMap = new HashMap<>()
        macAddressMap.put(MethodFieldUtils.EMPTY_STRING_DESC, MethodFieldUtils.NEW_STRING_WIFI_INFO_DESC)
        MethodCalledBean macAddress = new MethodCalledBean(
                MethodFieldUtils.WIFI_INFO_CLASS,
                MethodFieldUtils.GET_MAC_ADDRESS_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_STRING_DESC,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_MAC_ADDRESS_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                macAddressMap
        )
        addMethodCalledBean(macAddress)
        /**
         * IP地址
         */
        HashMap<String, String> ipAddressMap = new HashMap<>()
        ipAddressMap.put(MethodFieldUtils.EMPTY_INT_DESC, MethodFieldUtils.NEW_INT_WIFI_INFO_DESC)
        MethodCalledBean ipAddress = new MethodCalledBean(
                MethodFieldUtils.WIFI_INFO_CLASS,
                MethodFieldUtils.GET_IP_ADDRESS_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_INT_DESC,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_IP_ADDRESS_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                ipAddressMap
        )
        addMethodCalledBean(ipAddress)
        /**
         * 获取AndroidId
         */
        HashMap<String, String> androidIdMap = new HashMap<>()
        androidIdMap.put(MethodFieldUtils.CONTENTRESOLVER_STRING_DESC, MethodFieldUtils.CONTENTRESOLVER_STRING_DESC)
        MethodCalledBean androidId = new MethodCalledBean(
                MethodFieldUtils.SETTINGS_SECURE_CLASS,
                MethodFieldUtils.GET_STRING_METHOD_NAME,
                MethodFieldUtils.CONTENTRESOLVER_STRING_RETURN_STRING_DESC,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_STRING_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                androidIdMap
        )
        addMethodCalledBean(androidId)

        HashMap<String, String> scanResultMap = new HashMap<>()
        scanResultMap.put(MethodFieldUtils.EMPTY_LIST_DESC, MethodFieldUtils.NEW_EMPTY_LIST_DESC)
        MethodCalledBean scanResults = new MethodCalledBean(
                MethodFieldUtils.WIFI_MANAGER_CLASS,
                MethodFieldUtils.GET_SCAN_RESULTS_METHOD_NAME,
                MethodFieldUtils.EMPTY_RETURN_LIST,
                MethodFieldUtils.NEW_METHOD_OWNER,
                MethodFieldUtils.GET_SCAN_RESULTS_METHOD_NAME,
                MethodFieldUtils.STATIC_OPCODE,
                scanResultMap
        )
        addMethodCalledBean(scanResults)
    }

    static void addMethodCalledBean(MethodCalledBean methodCalledBean) {
        for (String desc : methodCalledBean.getMethodDescriptor()) {
            methodCalledBeans.put(methodCalledBean.getMethodOwner() + methodCalledBean.getMethodName() + desc, methodCalledBean)
        }
    }

}