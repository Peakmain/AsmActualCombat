package com.peakmain.analytics.plugin.visitor

class MethodFieldUtils{
    static String getTimeFieldName(String methodName) {
        return "timer_" + methodName;
    }
}