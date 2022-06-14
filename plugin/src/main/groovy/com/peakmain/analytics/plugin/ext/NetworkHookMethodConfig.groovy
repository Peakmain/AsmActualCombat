package com.peakmain.analytics.plugin.ext

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import com.peakmain.analytics.plugin.utils.NetworkFieldUtils

class NetworkHookMethodConfig {
    public final static HashMap<String, MethodCalledBean> methodCalledBeans = new HashMap<>()
    static {
        /**
         * 七牛云
         */
        HashMap<String, String> qiNiuMap = new HashMap<>()
        qiNiuMap.put(NetworkFieldUtils.DESCRIPTOR_QI_NIU_HTTP_USER_AGENT, NetworkFieldUtils.DESCRIPTOR_NEW_QI_NIU_HTTP_USER_AGENT)
        MethodCalledBean qiNiuBean = new MethodCalledBean(
                NetworkFieldUtils.OWNER_QI_NIU_HTTP_USER_AGENT,
                NetworkFieldUtils.NAME_QI_NIU_HTTP_USER_AGENT,
                NetworkFieldUtils.QI_NIU_METHOD_DESCRIPTOR,
                NetworkFieldUtils.OWNER_NEW_QI_NIU_HTTP_USER_AGENT,
                NetworkFieldUtils.NAME_QI_NIU_HTTP_USER_AGENT,
                MethodFieldUtils.STATIC_OPCODE,
                qiNiuMap)
        addMethodCalledBean(qiNiuBean)
        /**
         * Mob GET
         */
        HashMap<String, String> mobGetMap = new HashMap<>()
        mobGetMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_HTTP, NetworkFieldUtils.DESCRIPTOR_NEW_MOB_HTTP)
        MethodCalledBean mobMethodBean = new MethodCalledBean(
                NetworkFieldUtils.OWNER_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_HTTP,
                NetworkFieldUtils.MOB_GET_METHOD_DESCRIPTOR,
                NetworkFieldUtils.OWNER_NEW_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_HTTP,
                MethodFieldUtils.STATIC_OPCODE,
                mobGetMap)
        addMethodCalledBean(mobMethodBean)
    }
    static void addMethodCalledBean(MethodCalledBean methodCalledBean) {
        for (String desc : methodCalledBean.getMethodDescriptor()) {
            methodCalledBeans.put(methodCalledBean.getMethodOwner() + methodCalledBean.getMethodName() + desc, methodCalledBean)
        }
    }
}