package com.peakmain.analytics.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuryPointPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        BuryPointExtension extension = project.extensions.create("peakmainPlugin", BuryPointExtension)
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
            AppExtension appExtension = project.extensions.findByType(AppExtension.class)
            appExtension.registerTransform(new BuryPointTransform(project,extension))
        }else{
            println("------------您已关闭了埋点插件--------------")
        }
    }
}