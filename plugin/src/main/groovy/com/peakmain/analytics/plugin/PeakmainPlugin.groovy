package com.peakmain.analytics.plugin

import com.android.build.gradle.AppExtension
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.transform.PeakmainTransform
import com.peakmain.analytics.plugin.utils.log.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * author ：Peakmain
 * createTime：1/19/22
 * mail:2726449200@qq.com
 * describe：
 */
class PeakmainPlugin implements Plugin<Project> {
    private final String pluginName = "monitorPlugin"

    @Override
    void apply(Project project) {
        MonitorConfig extension = project.extensions.create(pluginName, MonitorConfig)
        boolean disablePlugin = false
        Properties properties = new Properties()
        //gradle.properties是否存在
        if (project.rootProject.file('gradle.properties').exists()) {
            //gradle.properties文件->输入流
            properties.load(project.rootProject.file('gradle.properties').newDataInputStream())
            disablePlugin = Boolean.parseBoolean(properties.getProperty("monitorPlugin.disableAppPlugin", "false"))
        }
        //如果disablePlugin可用
        if (!disablePlugin) {
            Logger.printPluginStart()
            project.afterEvaluate {
                extension.convertConfig()
            }
            AppExtension appExtension = project.extensions.findByType(AppExtension.class)
            appExtension.registerTransform(new PeakmainTransform(project, extension))
        } else {
            println("------------您已关闭了埋点插件--------------")
        }
    }

}
