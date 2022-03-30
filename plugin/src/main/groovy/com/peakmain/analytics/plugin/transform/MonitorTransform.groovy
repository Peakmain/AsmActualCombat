package com.peakmain.analytics.plugin.transform

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.utils.log.Logger
import com.peakmain.analytics.plugin.visitor.PeakmainVisitor
import org.objectweb.asm.ClassVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.gradle.api.Project

import java.util.concurrent.Callable
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class MonitorTransform extends Transform {
    private static Project project
    private MonitorConfig monitorConfig
    private WaitableExecutor waitableExecutor

    MonitorTransform(Project project) {
        this.project = project
    }

    void setMonitorConfig(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig
        if (!monitorConfig.disableMultiThreadBuild) {
            waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool()
        }
    }

    @Override
    String getName() {
        return "Peakmain"
    }
    /**
     * 需要处理的数据类型，有两种枚举类型
     * CLASS->处理的java的class文件
     * RESOURCES->处理java的资源
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }
    /**
     * 指 Transform 要操作内容的范围，官方文档 Scope 有 7 种类型：
     * 1. EXTERNAL_LIBRARIES        只有外部库
     * 2. PROJECT                   只有项目内容
     * 3. PROJECT_LOCAL_DEPS        只有项目的本地依赖(本地jar)
     * 4. PROVIDED_ONLY             只提供本地或远程依赖项
     * 5. SUB_PROJECTS              只有子项目。
     * 6. SUB_PROJECTS_LOCAL_DEPS   只有子项目的本地依赖项(本地jar)。
     * 7. TESTED_CODE               由当前变量(包括依赖项)测试的代码
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }
    /**
     * 是否增量编译
     * @return
     */
    @Override
    boolean isIncremental() {
        return monitorConfig.isIncremental
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        _transform(transformInvocation.context, transformInvocation.inputs, transformInvocation.outputProvider)
    }
    /**
     *
     * @param context
     * @param inputs 有两种类型，一种是目录，一种是 jar 包，要分开遍历
     * @param outputProvider 输出路径
     */
    void _transform(Context context, Collection<TransformInput> inputs, TransformOutputProvider outputProvider) throws IOException, TransformException, InterruptedException {
        println(monitorConfig.toString())
        long startTime = System.currentTimeMillis()
        if (!incremental) {
            //不是增量更新删除所有的outputProvider
            outputProvider.deleteAll()
        }
        inputs.each { TransformInput input ->
            //遍历目录
            input.directoryInputs.each { DirectoryInput directoryInput ->
                if (waitableExecutor) {
                    waitableExecutor.execute(new Callable<Object>() {
                        @Override
                        Object call() throws Exception {
                            handleDirectoryInput(directoryInput, outputProvider)
                            return null
                        }
                    })
                } else {
                    handleDirectoryInput(directoryInput, outputProvider)
                }
            }
            // 遍历jar 第三方引入的 class
            input.jarInputs.each { JarInput jarInput ->
                if (waitableExecutor) {
                    waitableExecutor.execute(new Callable<Object>() {
                        @Override
                        Object call() throws Exception {
                            handleJarInput(jarInput, outputProvider)
                            return null
                        }
                    })
                } else {
                    handleJarInput(jarInput, outputProvider)
                }
            }
        }
        if (waitableExecutor) {
            waitableExecutor.waitForTasksWithQuickFail(true)
        }
        println("[MonitorTransform]: 此次编译共耗时:${System.currentTimeMillis() - startTime}毫秒")
    }

    static void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        if (directoryInput.file.isDirectory()) {
            directoryInput.file.eachFileRecurse { File file ->
                String name = file.name
                if (filterClass(name)) {
                    // 用来读 class 信息
                    ClassReader classReader = new ClassReader(file.bytes)
                    // 用来写
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    //todo 改这里就可以了
                    ClassVisitor classVisitor = new PeakmainVisitor(classWriter)
                    // 下面还可以包多层
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                    // 重新覆盖写入文件
                    byte[] code = classWriter.toByteArray()
                    FileOutputStream fos = new FileOutputStream(
                            file.parentFile.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }
        // 把修改好的数据，写入到 output
        def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                directoryInput.scopes, Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    static void handleJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        if (jarInput.file.absolutePath.endsWith(".jar")) {
            // 重名名输出文件,因为可能同名,会覆盖
            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }
            JarFile jarFile = new JarFile(jarInput.file)
            Enumeration enumeration = jarFile.entries()
            File tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_temp.jar")
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
            //用于保存
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream inputStream = jarFile.getInputStream(jarEntry)
                //插桩class
                if (filterClass(entryName)) {
                    //class文件处理
                    jarOutputStream.putNextEntry(zipEntry)
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    //todo 改这里就可以了
                    ClassVisitor classVisitor = new PeakmainVisitor(classWriter)
                    // 下面还可以包多层
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                    byte[] code = classWriter.toByteArray()
                    jarOutputStream.write(code)
                } else {
                    jarOutputStream.putNextEntry(zipEntry)
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                jarOutputStream.closeEntry()
            }
            //结束
            jarOutputStream.close()
            jarFile.close()
            def dest = outputProvider.getContentLocation(jarName + md5Name,
                    jarInput.contentTypes, jarInput.scopes, Format.JAR)
            FileUtils.copyFile(tmpFile, dest)
            tmpFile.delete()
        }
    }

    static boolean filterClass(String className) {
        return (className.endsWith(".class") && !className.startsWith("R\$")
                && "R.class" != className && "BuildConfig.class" != className)
    }
}