package com.peakmain.analytics.plugin.utils

class MethodFieldUtils{
    static String getTimeFieldName(String methodName) {
        return "timer_" + methodName;
    }
}