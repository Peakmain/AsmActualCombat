package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
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
    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    MonitorMethodCalledReplaceAdapter(MethodVisitor mv, int access, String name, String desc, ClassVisitor classVisitor) {
        super(mv, access, name, desc)
        mAccess = access
        this.classVisitor = classVisitor
    }

    @Override
    void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        HashMap<String, MethodCalledBean> methodReplaceBeans = MonitorHookMethodConfig.methodCalledBeans
        String desc = owner + name + descriptor
        if (methodReplaceBeans.containsKey(desc)) {
            MethodCalledBean bean = methodReplaceBeans.get(desc)
            if (bean.newMethodOwner != null)
                super.visitMethodInsn(bean.newOpcode, bean.newMethodOwner, bean.newMethodName, bean.newMethodDescriptor.get(descriptor), false)
            else
                super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        } else {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }
}