package com.peakmain.analytics.plugin.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.utils.log.Logger
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.concurrent.Callable

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
        _transform(transformInvocation.context, transformInvocation.inputs, transformInvocation.outputProvider, transformInvocation.isIncremental())
    }
    /**
     *
     * @param context
     * @param inputs 有两种类型，一种是目录，一种是 jar 包，要分开遍历
     * @param outputProvider 输出路径
     */
    void _transform(Context context, Collection<TransformInput> inputs,
                    TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        println(monitorConfig.toString())
        long startTime = System.currentTimeMillis()
        if (!isIncremental) {
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
                            handleDirectoryInput(context, directoryInput, outputProvider, monitorConfig,isIncremental)
                            return null
                        }
                    })
                } else {
                    handleDirectoryInput(context, directoryInput, outputProvider, monitorConfig,isIncremental)
                }
            }
            // 遍历jar 第三方引入的 class
            input.jarInputs.each { JarInput jarInput ->
                if (waitableExecutor) {
                    waitableExecutor.execute(new Callable<Object>() {
                        @Override
                        Object call() throws Exception {
                            handleJarInput(context, jarInput, outputProvider, monitorConfig,isIncremental)
                            return null
                        }
                    })
                } else {
                    handleJarInput(context, jarInput, outputProvider, monitorConfig,isIncremental)
                }
            }
        }
        if (waitableExecutor) {
            waitableExecutor.waitForTasksWithQuickFail(true)
        }
        println("[MonitorTransform]: 此次编译共耗时:${System.currentTimeMillis() - startTime}毫秒")
    }

    void handleDirectoryInput(Context context, DirectoryInput directoryInput, TransformOutputProvider outputProvider, MonitorConfig monitorConfig,boolean isIncremental) {
        File dir = directoryInput.file
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
                directoryInput.getContentTypes(), directoryInput.getScopes(),
                Format.DIRECTORY)
        FileUtils.forceMkdir(dest)
        String srcDirPath = dir.absolutePath
        String destDirPath = dest.absolutePath
        if (isIncremental) {
            Map<File, Status> fileStatusMap = directoryInput.getChangedFiles()
            for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                Status status = changedFile.getValue()
                File inputFile = changedFile.getKey()
                String destFilePath = inputFile.absolutePath.replace(srcDirPath, destDirPath)
                File destFile = new File(destFilePath)
                switch (status) {
                    case Status.NOTCHANGED:
                        break
                    case Status.REMOVED:
                        Logger.info("目录 status = $status:$inputFile.absolutePath")
                        if (destFile.exists()) {
                            //noinspection ResultOfMethodCallIgnored
                            destFile.delete()
                        }
                        break
                    case Status.ADDED:
                    case Status.CHANGED:
                        Logger.info("目录 status = $status:$inputFile.absolutePath")
                        File modified = MonitorAnalyticsTransform.modifyClassFile(dir, inputFile, context.getTemporaryDir(),monitorConfig)
                        if (destFile.exists()) {
                            destFile.delete()
                        }
                        if (modified != null) {
                            FileUtils.copyFile(modified, destFile)
                            modified.delete()
                        } else {
                            FileUtils.copyFile(inputFile, destFile)
                        }
                        break
                    default:
                        break
                }
            }
        }else {
            FileUtils.copyDirectory(dir, dest)
            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                File inputFile ->
                    forEachDir(dir, inputFile, context, srcDirPath, destDirPath)
            }
        }

    }
    void forEachDir(File dir, File inputFile, Context context, String srcDirPath, String destDirPath) {
        File modified = MonitorAnalyticsTransform.modifyClassFile(dir, inputFile, context.getTemporaryDir(),monitorConfig)
        if (modified != null) {
            File target = new File(inputFile.absolutePath.replace(srcDirPath, destDirPath))
            if (target.exists()) {
                target.delete()
            }
            FileUtils.copyFile(modified, target)
            modified.delete()
        }
    }
    void handleJarInput(Context context, JarInput jarInput, TransformOutputProvider outputProvider, MonitorConfig monitorConfig,boolean isIncremental) {
        //获得输出文件
        File destFile = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        if (isIncremental) {
            Status status = jarInput.getStatus()
            switch (status) {
                case Status.NOTCHANGED:
                    break
                case Status.ADDED:
                case Status.CHANGED:
                    Logger.info("jar status = $status:$destFile.absolutePath")
                    transformJar(context,jarInput, outputProvider, monitorConfig)
                    break
                case Status.REMOVED:
                    Logger.info("jar status = $status:$destFile.absolutePath")
                    if (destFile.exists()) {
                        FileUtils.forceDelete(destFile)
                    }
                    break
                default:
                    break
            }
        } else {
            transformJar(context,jarInput, outputProvider, monitorConfig)
        }

    }
    void transformJar(Context context,JarInput jarInput,TransformOutputProvider outputProvider,MonitorConfig monitorConfig1) {
        String destName = jarInput.file.name

        /**截取文件路径的 md5 值重命名输出文件,因为可能同名,会覆盖*/
        def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8)
        /** 获取 jar 名字*/
        if (destName.endsWith(".jar")) {
            destName = destName.substring(0, destName.length() - 4)
        }

        /** 获得输出文件*/
        File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        def modifiedJar = MonitorAnalyticsTransform.modifyJar(jarInput.file, context.getTemporaryDir(), true, monitorConfig)
        if (modifiedJar == null) {
            modifiedJar = jarInput.file
        }
        FileUtils.copyFile(modifiedJar, dest)
    }

}