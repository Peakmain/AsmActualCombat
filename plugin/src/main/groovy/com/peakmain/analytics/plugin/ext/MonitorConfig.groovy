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
