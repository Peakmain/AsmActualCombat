package com.peakmain.analytics.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.android.tools.r8.naming.P
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.visitor.PeakmainVisitor
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor

abstract class MonitorAsmFactory implements AsmClassVisitorFactory<Params> {
    private static HashSet<String> exclude = new HashSet<>(['com.peakmain.sdk',
                                                            'android.support',
                                                            'androidx',
                                                            'com.google.android',
                                                            'com.bumptech.glide',
                                                            'com.tencent.tinker'])
    /** 将一些特例需要排除在外 */
    private static final HashSet<String> special = ['android.support.design.widget.TabLayout$ViewPagerOnTabSelectedListener',
                                                    'com.google.android.material.tabs.TabLayout$ViewPagerOnTabSelectedListener',
                                                    'android.support.v7.app.ActionBarDrawerToggle',
                                                    'androidx.appcompat.app.ActionBarDrawerToggle',
                                                    'androidx.fragment.app.FragmentActivity',
                                                    'androidx.core.app.NotificationManagerCompat',
                                                    'androidx.core.app.ComponentActivity',
                                                    'android.support.v4.app.NotificationManagerCompat',
                                                    'android.support.v4.app.SupportActivity',
                                                    'cn.jpush.android.service.PluginMeizuPlatformsReceiver',
                                                    'androidx.appcompat.widget.ActionMenuPresenter$OverflowMenuButton',
                                                    'android.widget.ActionMenuPresenter$OverflowMenuButton',
                                                    'android.support.v7.widget.ActionMenuPresenter$OverflowMenuButton']

    interface Params extends InstrumentationParameters {
        @Input
        Property<Integer> getMethodStatus()

        @Input
        ListProperty<String> getExceptSet()

        @Input
        Property<String> getInterceptPackageName()

        @Input
        Property<Boolean> getDisableStackMapFrame()

        @Input
        Property<Integer> getStatus()

        @Input
        ListProperty<String> getWhiteList()
    }

    @Override
    ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor nextClassVisitor) {
        // 每次调用都创建一个临时的 config 对象传给 Visitor
        Params params = getParameters().get()// 获取参数容器

        MonitorConfig config = new MonitorConfig()
        // 强制使用方法调用 getMethodStatus() 而不是属性简写
        config.methodStatus = params.getMethodStatus().getOrElse(0)
        config.interceptPackageName = params.getInterceptPackageName().getOrElse("")
        config.exceptSet = new HashSet<>(params.getExceptSet().getOrElse([]))
        config.disableStackMapFrame = params.getDisableStackMapFrame()
        config.status = params.getStatus().get()
        config.whiteList = new ArrayList<>(params.getWhiteList().getOrElse([]))
        config.convertConfig()
        // 返回你的 Visitor
        return new PeakmainVisitor(nextClassVisitor, config)

    }

    @Override
    boolean isInstrumentable(ClassData classData) {
        String className = classData.className // 注意：这是全路径类名，如 com.example.Test
        if (isAndroidGenerated(className)) return false
        // 特例检查
        for (String pkgName in special) {
            if (className.startsWith(pkgName)) return true
        }

        // 排除项检查
        if (className.startsWith("android.support.v17.leanback") || className.startsWith("androidx.leanback")) {
            return true
        }
        for (String pkgName in exclude) {
            if (className.startsWith(pkgName)) return false
        }
        // 最后检查用户自定义的排除列表
        Params myParams = (Params) getParameters().get()
        List<String> excepts = myParams.getExceptSet().getOrElse([])
        String internalName = className.replace(".", "/")
        return !excepts.contains(internalName)
    }

    private static boolean isAndroidGenerated(String className) {
        return className.contains('R$') ||
                className.contains('R2$') ||
                className.endsWith('.R') ||
                className.endsWith('.R2') ||
                className.contains('BuildConfig')
    }
}