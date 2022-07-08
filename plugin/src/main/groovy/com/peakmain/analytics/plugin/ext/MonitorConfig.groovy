package com.peakmain.analytics.plugin.ext

import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import org.apache.http.util.TextUtils

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
    //是否拦截网络
    public boolean isInterceptNetworks = true
    /**
     * 隐私方法方法的状态
     * @params 1 代表替换方法体
     * @params 其他都
     */
    public int methodStatus = 0
    //是否开启日志
    public boolean enableLog
    private MethodFieldUtils.StatusEnum statusEnum = MethodFieldUtils.StatusEnum.METHOD_STATE_NORMAL
    private final HashSet<String> special = [
            'com.peakmain.sdk.utils.SensorsDataUtils',
            'androidx.core.app.NotificationManagerCompat',
            'android.support.v4.app.NotificationManagerCompat']
    HashSet<String> exceptSet = new HashSet<>()
    /**
     * 是否禁用开启堆栈分析，默认是禁用
     */
    public boolean disableStackMapFrame = true
    /**
     * 拦截点击的包名前缀
     */
    public String interceptPackageName = ""

    void convertConfig() {
        for (String value : special) {
            value = value.replace(".", "/")
            exceptSet.add(value)
        }
        for (int i = 0; i < whiteList.size(); i++) {
            whiteList.set(i, whiteList.get(i).replace(".", "/"))
        }
        println("当前方法的Status:" + methodStatus)
        if (methodStatus == MethodFieldUtils.StatusEnum.METHOD_STATE_REPLACE.value) {
            statusEnum = MethodFieldUtils.StatusEnum.METHOD_STATE_REPLACE
        } else {
            statusEnum = MethodFieldUtils.StatusEnum.METHOD_STATE_NORMAL
        }
        if (!TextUtils.isEmpty(interceptPackageName)) {
            interceptPackageName = interceptPackageName.replace(".", ",")
        }
    }

    MethodFieldUtils.StatusEnum getStatusEnum() {
        return statusEnum
    }

    void reset() {
        //清空白名单
        whiteList.clear()
    }

    @Override
    String toString() {
        return "MonitorPlugin:\n[\n\t是否开启多线程编译:${!disableMultiThreadBuild},\n" +
                "\t是否开启增量更新:${!isIncremental},\n\t白名单是:${listToString(whiteList)}\n]"

    }

    static String listToString(ArrayList<String> value) {
        Iterator<String> it = value.iterator()
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('\n\t[\n');
        for (; ;) {
            String e = it.next()
            sb.append("\t\t").append(e)
            if (!it.hasNext())
                return sb.append('\n\t]').toString();
            sb.append(',\n').append(' ');
        }
    }
}
