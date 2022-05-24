package com.peakmain.analytics.plugin.transform

import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.visitor.PeakmainVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.utils.IOUtils
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
                                                            'com.qiyukf',
                                                            'android.arch',
                                                            'com.google.android',
                                                            "com.tencent.smtt",
                                                            "com.umeng.message",
                                                            "com.xiaomi.push",
                                                            "com.huawei.hms",
                                                            "cn.jpush.android",
                                                            "cn.jiguang",
                                                            "com.meizu.cloud.pushsdk",
                                                            "com.vivo.push",
                                                            "com.igexin",
                                                            "com.getui",
                                                            "com.xiaomi.mipush.sdk",
                                                            "com.heytap.msp.push",
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

    static File modifyClassFile(File dir, File classFile, File tempDir, MonitorConfig monitorConfig) {
        File modified = null
        try {
            String className = path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
            byte[] modifiedClassBytes = modifyClass(sourceClassBytes, monitorConfig)
            if (modifiedClassBytes) {
                modified = new File(tempDir, className.replace('.', '') + '.class')
                if (modified.exists()) {
                    modified.delete()
                }
                modified.createNewFile()
                new FileOutputStream(modified).write(modifiedClassBytes)
            }
        } catch (Exception e) {
            e.printStackTrace()
            modified = classFile
        }
        return modified
    }

    private static byte[] modifyClass(byte[] srcClass, MonitorConfig monitorConfig) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        PeakmainVisitor classVisitor = new PeakmainVisitor(classWriter, monitorConfig)
        ClassReader cr = new ClassReader(srcClass)
        cr.accept(classVisitor, ClassReader.SKIP_FRAMES)
        return classWriter.toByteArray()
    }

    static File modifyJar(File jarFile, File tempDir, boolean nameHex, MonitorConfig monitorConfig) {
        /**
         * 读取原 jar
         */
        def file = new JarFile(jarFile, false)

        /**
         * 设置输出到的 jar
         */
        def hexName = ""
        if (nameHex) {
            hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        }
        def outputJar = new File(tempDir, hexName + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        Enumeration enumeration = file.entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            InputStream inputStream
            try {
                inputStream = file.getInputStream(jarEntry)
            } catch (Exception e) {
                return null
            }
            String entryName = jarEntry.getName()
            if (entryName.endsWith(".DSA") || entryName.endsWith(".SF")) {
                //ignore
            } else {
                String className
                JarEntry jarEntry2 = new JarEntry(entryName)
                jarOutputStream.putNextEntry(jarEntry2)

                byte[] modifiedClassBytes = null
                byte[] sourceClassBytes = IOUtils.toByteArray(inputStream)
                if (entryName.endsWith(".class")) {
                    className = entryName.replace(Matcher.quoteReplacement(File.separator), ".").replace(".class", "")
                    if (isShouldModify(className)) {
                        modifiedClassBytes = modifyClass(sourceClassBytes, monitorConfig)
                    }
                }
                if (modifiedClassBytes == null) {
                    modifiedClassBytes = sourceClassBytes
                }
                jarOutputStream.write(modifiedClassBytes)
                jarOutputStream.closeEntry()
            }
        }
        jarOutputStream.close()
        file.close()
        return outputJar
    }

    static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }
}