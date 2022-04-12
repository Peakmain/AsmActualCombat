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
    String newMethodOwner
    String newMethodName
    int newOpcode

    HashMap<String, String> newMethodDescriptor


    MethodCalledBean(String methodOwner, String methodName, String[] methodDescriptor) {
        this.methodOwner = methodOwner
        this.methodName = methodName
        this.methodDescriptor = methodDescriptor
    }

    MethodCalledBean(String methodOwner, String methodName, String[] methodDescriptor, String newMethodOwner, String newMethodName, int newOpcode, HashMap<String, String> newMethodDescriptor) {
        this.methodOwner = methodOwner
        this.methodName = methodName
        this.methodDescriptor = methodDescriptor
        this.newMethodOwner = newMethodOwner
        this.newMethodName = newMethodName
        this.newOpcode = newOpcode
        this.newMethodDescriptor = newMethodDescriptor
    }
}
