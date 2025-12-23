package com.peakmain.analytics.plugin


import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant

// 必须添加这一行

import com.peakmain.analytics.plugin.ext.MonitorConfig

import com.peakmain.analytics.plugin.utils.log.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.peakmain.analytics.plugin.MonitorAsmFactory

/**
 * author ：Peakmain
 * createTime：1/19/22
 * mail:2726449200@qq.com
 * describe：
 */
class PeakmainPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        MonitorConfig extension = project.extensions.create('monitorPlugin', MonitorConfig)
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

         /*   AppExtension appExtension = project.extensions.findByType(AndroidComponentsExtension.class)
            def transform = new MonitorTransform(project)
            appExtension.registerTransform(transform)
            project.afterEvaluate {
                extension.convertConfig()
                transform.monitorConfig = extension
            }*/

            // 获取新版的组件扩展
            def androidComponents = project.extensions.findByType(AndroidComponentsExtension.class)

            if (androidComponents != null) {
                // 在 Variant 数字化阶段注册插桩
                androidComponents.onVariants(androidComponents.selector().all()) { Variant variant ->
                    // 注册新的 ASM 工厂
                    extension.convertConfig()
                    variant.instrumentation.transformClassesWith(
                            MonitorAsmFactory.class,
                            com.android.build.api.instrumentation.InstrumentationScope.ALL // 相当于旧版的 TransformManager.SCOPE_FULL_PROJECT
                    ) { params ->
                        // 使用 "as" 关键字或强制类型转换，确保调用的是接口方法
                        params.getMethodStatus().set(extension.methodStatus)
                        params.getInterceptPackageName().set(extension.interceptPackageName)
                        params.getExceptSet().set(extension.exceptSet.toList()) // 将 HashSet 转为 List
                        params.getDisableStackMapFrame().set(extension.disableStackMapFrame)
                        params.getStatus().set(extension.getStatus())
                    }

                    // 设置栈帧计算模式
                    variant.instrumentation.setAsmFramesComputationMode(
                            com.android.build.api.instrumentation. FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS
                    )
                }
            }
        } else {
            println("------------您已关闭了埋点插件--------------")
        }
    }

}
