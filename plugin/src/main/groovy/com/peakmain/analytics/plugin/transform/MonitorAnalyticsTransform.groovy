package com.peakmain.analytics.plugin.transform

import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.visitor.PeakmainVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.regex.Matcher

class MonitorAnalyticsTransform {
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
    /**
     * 过滤不需要修改的class
     */
    protected static boolean isShouldModify(String className) {
        boolean isShouldModify = false
        if (!isAndroidGenerated(className)) {
            for (pkgName in special) {
                if (className.startsWith(pkgName)) {
                    return true
                }
            }
            isShouldModify = true
            if (!isLeanback(className)) {
                for (pkgName in exclude) {
                    if (className.startsWith(pkgName)) {
                        isShouldModify = false
                        break
                    }
                }
            }
        }
        return isShouldModify
    }

    private static boolean isLeanback(String className) {
        return className.startsWith("android.support.v17.leanback") || className.startsWith("androidx.leanback")
    }

    private static boolean isAndroidGenerated(String className) {
        return className.contains('R$') ||
                className.contains('R2$') ||
                className.contains('R.class') ||
                className.contains('R2.class') ||
                className.contains('BuildConfig.class')
    }

    private static byte[] modifyClass(byte[] srcClass, MonitorConfig monitorConfig) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        PeakmainVisitor classVisitor = new PeakmainVisitor(classWriter, monitorConfig)
        ClassReader cr = new ClassReader(srcClass)
        cr.accept(classVisitor, ClassReader.SKIP_FRAMES)
        return classWriter.toByteArray()
    }

    static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }
}