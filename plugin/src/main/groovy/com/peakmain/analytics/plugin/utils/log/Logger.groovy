package com.peakmain.analytics.plugin.utils.log
/***
 *                    .::::.
 *                  .::::::::.
 *                 :::::::::::  FUCK YOU
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 */
class Logger {
    private static final String VERSION = "1.0.1"
    private static boolean debug = false

    static void printPluginStart() {
        println()
        println("${LogUI.C_BLACK_GREEN.value}" + "####################################################################" + "${LogUI.E_NORMAL.value}")
        println("${LogUI.C_BLACK_GREEN.value}" + "########                                                    ########" + "${LogUI.E_NORMAL.value}")
        println("${LogUI.C_BLACK_GREEN.value}" + "########                                                    ########" + "${LogUI.E_NORMAL.value}")
        println("${LogUI.C_BLACK_GREEN.value}" + "########              欢迎使用PeakmainPlugin埋点编译插件 v" + VERSION + "${LogUI.E_NORMAL.value}")
        println("${LogUI.C_BLACK_GREEN.value}" + "########                                                    ########" + "${LogUI.E_NORMAL.value}")
        println("${LogUI.C_BLACK_GREEN.value}" + "########                                                    ########" + "${LogUI.E_NORMAL.value}")
        println("${LogUI.C_BLACK_GREEN.value}" + "####################################################################" + "${LogUI.E_NORMAL.value}")
        println()
    }
    /**
     * 设置是否打印日志
     */
    static void setDebug(boolean isDebug) {
        debug = isDebug
    }

    def static error(Object msg) {
        if (!debug)
            return
        try {
            println("${LogUI.C_ERROR.value}[PeakmainPlugin]: ${msg}${LogUI.E_NORMAL.value}")
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def static warn(Object msg) {
        if (!debug)
            return
        try {
            println("${LogUI.C_WARN.value}[PeakmainPlugin]: ${msg}${LogUI.E_NORMAL.value}")
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def static logForEach(Object... msg) {
        if (!debug) {
            return
        }
        msg.each {
            Object m ->
                try {
                    if (m != null) {
                        if (m.class.isArray()) {
                            print "["
                            def length = Array.getLength(m);
                            if (length > 0) {
                                for (int i = 0; i < length; i++) {
                                    def get = Array.get(m, i);
                                    if (get != null) {
                                        print "${get}\t"
                                    } else {
                                        print "null\t"
                                    }
                                }
                            }
                            print "]\t"
                        } else {
                            print "${m}\t"
                        }
                    } else {
                        print "null\t"
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                }
        }
        println ""
    }


}