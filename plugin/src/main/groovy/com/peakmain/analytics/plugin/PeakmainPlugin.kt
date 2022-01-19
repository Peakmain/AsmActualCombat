package com.peakmain.analytics.plugin

import com.android.build.gradle.AppExtension
import com.peakmain.analytics.plugin.ext.PeakmainExtension
import com.peakmain.analytics.plugin.transform.PeakmainTransform
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
class PeakmainPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("peakmainPlugin", PeakmainExtension::class.java)
        var disableBuryPointPlugin = false
        val properties = Properties()
        if(project.rootProject.file("gradle.properties").exists()){
            properties.load(DataInputStream(FileInputStream(project.rootProject.file("gradle.properties"))))
            disableBuryPointPlugin=java.lang.Boolean.parseBoolean(properties.getProperty("peakmainPlugin.disableAppClick","false"))
        }
        if(!disableBuryPointPlugin){
           val appExtension= project.extensions.findByType(AppExtension::class.java)
            appExtension?.registerTransform(
                PeakmainTransform(
                    project,
                    extension
                )
            )
        }else{
            println("------------您已关闭了埋点插件--------------")
        }
    }

}
