package com.peakmain.analytics.plugin.ext

import com.android.tools.r8.u.b.N
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
        mobGetMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_GET_HTTP, NetworkFieldUtils.DESCRIPTOR_NEW_MOB_GET_HTTP)
        MethodCalledBean mobGetMethodBean = new MethodCalledBean(
                NetworkFieldUtils.OWNER_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_GET_HTTP,
                NetworkFieldUtils.MOB_GET_METHOD_DESCRIPTOR,
                NetworkFieldUtils.OWNER_NEW_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_GET_HTTP,
                MethodFieldUtils.STATIC_OPCODE,
                mobGetMap)
        addMethodCalledBean(mobGetMethodBean)
        /**
         * Mob RawGet
         */
        HashMap<String, String> mobRawGetMap = new HashMap<>()
        mobRawGetMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_RAW_GET_HTTP, NetworkFieldUtils.DESCRIPTOR_NEW_MOB_RAW_GET_HTTP)
        MethodCalledBean mobRawGetMethodBean = new MethodCalledBean(
                NetworkFieldUtils.OWNER_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_RAW_GET_HTTP,
                NetworkFieldUtils.MOB_RAW_GET_METHOD_DESCRIPTOR,
                NetworkFieldUtils.OWNER_NEW_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_RAW_GET_HTTP,
                MethodFieldUtils.STATIC_OPCODE,
                mobRawGetMap
        )
        addMethodCalledBean(mobRawGetMethodBean)
        /**
         * Mob Post
         */
        HashMap<String, String> mobJsonPostMap = new HashMap<>()
        mobJsonPostMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_JSON_POST_HTTP,NetworkFieldUtils.DESCRIPTOR_NEW_MOB_JSON_POST_HTTP)
        MethodCalledBean mobJsonPostMethodBean=new MethodCalledBean(
                NetworkFieldUtils.OWNER_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_JSON_POST_HTTP,
                NetworkFieldUtils.MOB_JSON_POST_METHOD_DESCRIPTOR,
                NetworkFieldUtils.DESCRIPTOR_NEW_MOB_JSON_POST_HTTP,
                NetworkFieldUtils.NAME_MOB_JSON_POST_HTTP,
                MethodFieldUtils.STATIC_OPCODE,
                mobJsonPostMap
        )
        addMethodCalledBean(mobJsonPostMethodBean)


    }

    static void addMethodCalledBean(MethodCalledBean methodCalledBean) {
        for (String desc : methodCalledBean.getMethodDescriptor()) {
            methodCalledBeans.put(methodCalledBean.getMethodOwner() + methodCalledBean.getMethodName() + desc, methodCalledBean)
        }
    }
}