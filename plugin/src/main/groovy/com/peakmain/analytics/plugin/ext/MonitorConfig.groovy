package com.peakmain.analytics.plugin.ext
/**
 * author ：Peakmain
 * createTime：2022/3/29
 * mail:2726449200@qq.com
 * describe：
 */
class MonitorConfig {
    /**
     * 是否禁用多线程构建
     */
    public boolean disableMultiThreadBuild = false
    /**
     * 是否开启增量更新
     */
    public boolean isIncremental = false
    public ArrayList<String> whiteList = []

    void convertConfig() {

    }

    void reset() {
        whiteList.each { info ->
            info.reset()
        }
    }

    @Override
    String toString() {
        return "是否禁用多线程构建:" + disableMultiThreadBuild + ";是否开启增量更新:" + isIncremental
    }
}