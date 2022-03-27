package com.peakmain.analytics.plugin

import com.android.build.gradle.AppExtension
import com.peakmain.analytics.plugin.ext.PeakmainExtension
import com.peakmain.analytics.plugin.transform.PeakmainTransform
import com.peakmain.analytics.plugin.utils.log.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.DataInputStream
import java.io.FileInputStream
import java.util.*


/**
 * author ：Peakmain
 * createTime：1/19/22
 * mail:2726449200@qq.com
 * describe：
 */
class PeakmainPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        PeakmainExtension extension = project.extensions.create("peakmainPlugin", PeakmainExtension)
        boolean disableBuryPointPlugin = false
        Properties properties = new Properties()
        //gradle.properties是否存在
        if(project.rootProject.file('gradle.properties').exists()){
            //gradle.properties文件->输入流
            properties.load(project.rootProject.file('gradle.properties').newDataInputStream())
            disableBuryPointPlugin=Boolean.parseBoolean(properties.getProperty("peakmainPlugin.disableAppClick","false"))
        }
        //如果disableBuryPointPlugin可用
        if(!disableBuryPointPlugin){
            Logger.printPluginStart()
            AppExtension appExtension = project.extensions.findByType(AppExtension.class)
            appExtension.registerTransform(new PeakmainTransform(project,extension))
        }else{
            println("------------您已关闭了埋点插件--------------")
        }
    }

}
