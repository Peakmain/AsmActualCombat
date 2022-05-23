package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.ext.MonitorHookMethodConfig
import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

import javax.swing.plaf.TextUI

/**
 * author ：Peakmain
 * createTime：2022/4/2
 * mail:2726449200@qq.com
 * describe：
 */
class MonitorMethodCalledReplaceAdapter extends MonitorDefalutMethodAdapter {
    private int mAccess
    private ClassVisitor classVisitor
    private String mClassName
    private MonitorConfig monitorConfig
    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorMethodCalledReplaceAdapter(MethodVisitor mv, int access, String name, String desc, ClassVisitor classVisitor, String className, MonitorConfig monitorConfig) {
        super(mv, access, name, desc)
        mAccess = access
        this.classVisitor = classVisitor
        mClassName = className
        this.monitorConfig = monitorConfig
    }

    @Override
    void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        HashMap<String, MethodCalledBean> methodReplaceBeans = MonitorHookMethodConfig.methodCalledBeans
        String desc = owner + name + descriptor
        if (!monitorConfig.whiteList.contains(mClassName) &&
                !monitorConfig.exceptSet.contains(mClassName) &&
                methodReplaceBeans.containsKey(desc)
                && (mClassName.contains('cn/jiguang') ||
                mClassName.contains('libcore/util/Jauns') ||
                mClassName.contains("com/alipay") ||
                mClassName.contains("com/amap") ||
                mClassName.contains("com/loc"))) {
            println("调用方法的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            MethodCalledBean bean = methodReplaceBeans.get(desc)
            super.visitMethodInsn(bean.newOpcode, bean.newMethodOwner, bean.newMethodName, bean.newMethodDescriptor.get(descriptor), false)
        } else {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }
}