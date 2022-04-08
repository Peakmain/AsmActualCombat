package com.peakmain.analytics.plugin.entity

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class MethodCalledBean {
    String methodOwner
    String methodName
    String[] methodDescriptor

    MethodCalledBean(String methodOwner, String methodName, String[] methodDescriptor) {
        this.methodOwner = methodOwner
        this.methodName = methodName
        this.methodDescriptor = methodDescriptor
    }

}
