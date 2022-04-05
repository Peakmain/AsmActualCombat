package com.peakmain.analytics.plugin.utils

class MethodFieldUtils{
    public static final String LOG_MANAGER = "com/peakmain/sdk/utils/LogManager"
    static String getTimeFieldName(String methodName) {
        return "timer_" + methodName;
    }
}