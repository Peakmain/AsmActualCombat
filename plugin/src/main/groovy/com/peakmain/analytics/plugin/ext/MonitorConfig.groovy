package com.peakmain.analytics.plugin.ext

import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import groovy.transform.CompileStatic

import java.io.Serializable
/**
 * author ：Peakmain
 * createTime：2022/3/29
 * mail:2726449200@qq.com
 * describe：
 */
@CompileStatic // 添加这个注解，强制静态编译，剔除 Groovy 动态元数据
class MonitorConfig implements Serializable{

    public ArrayList<String> whiteList = []
    //是否拦截网络
    public boolean isInterceptNetworks = false
    /**
     * 隐私方法方法的状态
     * @params 1 代表替换方法体
     * @params 其他都
     */
    public int methodStatus = 0
    //是否开启日志
    public boolean enableLog
    public Integer status = MethodFieldUtils.METHOD_STATE_NORMAL
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
        if (interceptPackageName.length()>0 && interceptPackageName != null) {
            interceptPackageName = interceptPackageName.replace(".", ",")
        }
    }

    int getStatus() {
        return status
    }

    void reset() {
        //清空白名单
        whiteList.clear()
    }

    @Override
    String toString() {
        return "MonitorPlugin:\n[\n" +
                "\t白名单是:${listToString(whiteList)}\n]"

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
