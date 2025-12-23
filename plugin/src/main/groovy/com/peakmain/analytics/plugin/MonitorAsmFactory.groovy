package com.peakmain.analytics.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.visitor.PeakmainVisitor
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor

abstract class MonitorAsmFactory implements AsmClassVisitorFactory<Params> {

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

        // 返回你的 Visitor
        return new PeakmainVisitor(nextClassVisitor, config)

    }

    @Override
    boolean isInstrumentable(ClassData classData) {
        // 1. 直接使用 parameters 成员
        // 2. 使用 get() 而不是 getExceptSet()
        Params myParams = (Params) getParameters().get()
        List<String> excepts = myParams.getExceptSet().getOrElse([])

        String className = classData.className
        String internalName = className.replace(".", "/")

        return !excepts.contains(internalName)
    }
}