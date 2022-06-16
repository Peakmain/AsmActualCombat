package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.ext.MonitorHookMethodConfig
import com.peakmain.analytics.plugin.utils.MethodFieldUtils
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
        String replacefm = MethodFieldUtils.OWNER_REFLEX_IOC_FM +
                MethodFieldUtils.NAME_REFLEX_IOC + MethodFieldUtils.DESCRIPTOR_REFLEX_IOC_FM

        String replace = MethodFieldUtils.OWNER_REFLEX_IOC_X + MethodFieldUtils.NAME_REFLEX_IOC +
                MethodFieldUtils.DESCRIPTOR_REFLEX_IOC_X
        if (mClassName.contains(MethodFieldUtils.CLASS_REFLEX_LOC) && (replace == desc)) {

            println("调用反射方法的class:" + mClassName + "调用反射的owner:" + owner + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            super.visitMethodInsn(MethodFieldUtils.STATIC_OPCODE,
                    MethodFieldUtils.NEW_METHOD_REFLEX_OWNER,
                    MethodFieldUtils.NAME_REFLEX_IOC,
                    MethodFieldUtils.DESCRIPTOR_REFLEX_IOC_X,
                    false)
        } else if (mClassName.contains(MethodFieldUtils.CLASS_REFLEX_LOC) && replacefm == desc) {
            println("调用反射方法的class:" + mClassName + "调用反射的owner:" + owner + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            super.visitMethodInsn(MethodFieldUtils.STATIC_OPCODE,
                    MethodFieldUtils.NEW_METHOD_REFLEX_OWNER,
                    MethodFieldUtils.NAME_REFLEX_IOC,
                    MethodFieldUtils.DESCRIPTOR_REFLEX_IOC_FM,
                    false)
        }  else if (!monitorConfig.whiteList.contains(mClassName) && !monitorConfig.exceptSet.contains(mClassName) && methodReplaceBeans.containsKey(desc)) {
            println("调用方法的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            MethodCalledBean bean = methodReplaceBeans.get(desc)
            super.visitMethodInsn(bean.newOpcode, bean.newMethodOwner, bean.newMethodName, bean.newMethodDescriptor.get(descriptor), false)
        } else {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }
}