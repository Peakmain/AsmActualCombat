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
        HashMap<String, String> mobPostMap = new HashMap<>()
        mobPostMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_POST_HTTP,NetworkFieldUtils.DESCRIPTOR_NEW_MOB_POST_HTTP)
        mobPostMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_POST_HTTP1,NetworkFieldUtils.DESCRIPTOR_NEW_MOB_POST_HTTP1)
        mobPostMap.put(NetworkFieldUtils.DESCRIPTOR_MOB_POST_HTTP2,NetworkFieldUtils.DESCRIPTOR_NEW_MOB_POST_HTTP2)
        MethodCalledBean mobPostMethodBean=new MethodCalledBean(
                NetworkFieldUtils.OWNER_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_POST_HTTP,
                NetworkFieldUtils.MOB_POST_METHOD_DESCRIPTOR,
                NetworkFieldUtils.OWNER_NEW_MOB_HTTP,
                NetworkFieldUtils.NAME_MOB_POST_HTTP,
                MethodFieldUtils.STATIC_OPCODE,
                mobPostMap
        )
        addMethodCalledBean(mobPostMethodBean)


        /**
         * GlideUrl getHead
         */
/*        HashMap<String, String> glideUrlMap = new HashMap<>()
        glideUrlMap.put(NetworkFieldUtils.DESCRIPTOR_GLIDE_URL_HTTP, NetworkFieldUtils.DESCRIPTOR_NEW_GLIDE_URL_HTTP)
        MethodCalledBean glideMethodBean = new MethodCalledBean(
                NetworkFieldUtils.OWNER_GLIDE_URL_HTTP,
                NetworkFieldUtils.NAME_GLIDE_URL_HTTP,
                NetworkFieldUtils.GLIDE_METHOD_DESCRIPTOR,
                NetworkFieldUtils.OWNER_NEW_GLIDE_URL_HTTP,
                NetworkFieldUtils.NAME_GLIDE_URL_HTTP,
                MethodFieldUtils.STATIC_OPCODE,
                glideUrlMap
        )
        addMethodCalledBean(glideMethodBean)*/


    }

    static void addMethodCalledBean(MethodCalledBean methodCalledBean) {
        for (String desc : methodCalledBean.getMethodDescriptor()) {
            methodCalledBeans.put(methodCalledBean.getMethodOwner() + methodCalledBean.getMethodName() + desc, methodCalledBean)
        }
    }
}