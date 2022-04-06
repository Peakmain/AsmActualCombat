package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.entity.PeakmainMethodCell
import com.peakmain.analytics.plugin.ext.MonitorConfig
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

import java.util.concurrent.ConcurrentHashMap

/**
 * author ：Peakmain
 * createTime：2021/1/5
 * mail:2726449200@qq.com
 * describe：
 */
class PeakmainVisitor extends ClassVisitor {
    private ClassVisitor classVisitor
    private String[] mInterfaces
    private HashMap<String, PeakmainMethodCell> mMethodCells = new HashMap<>()
    private String mClassName
    private MonitorConfig mMonitorConfig

    private ConcurrentHashMap<String, MethodCalledBean> methodCalledBeans = new ConcurrentHashMap<>()

    PeakmainVisitor(ClassVisitor classVisitor, MonitorConfig config) {
        super(Opcodes.ASM9, classVisitor)
        this.classVisitor = classVisitor
        this.mMonitorConfig = config
    }
    /**
     * @param version 类版本
     * @param access 修饰符
     * @param name 类名
     * @param signature 泛型信息
     * @param superName 父类
     * @param interfaces 实现的接口
     */
    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.mInterfaces = interfaces
        this.mClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }
    /**
     * 扫描类的方法进行调用
     * @param access 修饰符
     * @param name 方法名字
     * @param descriptor 方法签名
     * @param signature 泛型信息
     * @param exceptions 抛出的异常
     * @return
     */
    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        methodVisitor = new MonitorClickAdapter(methodVisitor, access, name, descriptor, mMethodCells, mInterfaces)
        methodVisitor = new MonitorPrintParametersReturnValueAdapter(methodVisitor, access, name, descriptor, mClassName, classVisitor)
        if (mMonitorConfig.disableDeviceId)
            methodVisitor = new MonitorMethodCalledClearAdapter(methodVisitor, access, name, descriptor, mClassName, methodCalledBeans)
        else if (mMonitorConfig.replaceDeviceId) {
            methodVisitor = new MonitorMethodCalledReplaceAdapter(methodVisitor, access, name, descriptor, classVisitor)
        }
        if (!mMonitorConfig.disableStackMapFrame)
            methodVisitor = new MonitorMethodStackMapFrameAdapter(mClassName, access, name, descriptor, methodVisitor)
        return methodVisitor
    }

    /**
     * 获取方法参数下标为 index 的对应 ASM index
     * @param types 方法参数类型数组
     * @param index 方法中参数下标，从 0 开始
     * @param isStaticMethod 该方法是否为静态方法
     * @return 访问该方法的 index 位参数的 ASM index
     */
    int getVisitPosition(Type[] types, int index, boolean isStaticMethod) {
        if (types == null || index < 0 || index >= types.length) {
            throw new Error("getVisitPosition error")
        }
        if (index == 0) {
            return isStaticMethod ? 0 : 1
        } else {
            return getVisitPosition(types, index - 1, isStaticMethod) + types[index - 1].getSize()
        }
    }

}
