package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.ext.MonitorHookMethodConfig
import com.peakmain.analytics.plugin.ext.NetworkHookMethodConfig
import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import com.peakmain.analytics.plugin.utils.NetworkFieldUtils
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type

/**
 * author ：Peakmain
 * createTime：2022/6/14
 * mail:2726449200@qq.com
 * describe：网络库的请求头部替换
 */
class NetworkMethodCalledReplaceAdapter extends MonitorDefalutMethodAdapter {
    private int mAccess
    private ClassVisitor classVisitor
    private String mClassName
    private MonitorConfig monitorConfig
    private String methodDesc
    private MethodVisitor methodVisitor
    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param mv @param access the method's access flags (see {@link Opcodes}).
     * @param name the method's name.
     * @param desc
     */
    NetworkMethodCalledReplaceAdapter(MethodVisitor mv, int access, String name, String desc, ClassVisitor classVisitor, String className, MonitorConfig monitorConfig) {
        super(mv, access, name, desc)
        mAccess = access
        this.classVisitor = classVisitor
        mClassName = className
        this.monitorConfig = monitorConfig
        methodDesc = desc
        methodVisitor = mv
    }


    @Override
    void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        HashMap<String, MethodCalledBean> methodReplaceBeans = NetworkHookMethodConfig.methodCalledBeans
        String desc = owner + name + descriptor
        if(methodReplaceBeans.containsKey(desc)){
            println("拦截网络请求的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            MethodCalledBean bean = methodReplaceBeans.get(desc)
            super.visitMethodInsn(bean.newOpcode, bean.newMethodOwner, bean.newMethodName, bean.newMethodDescriptor.get(descriptor), false)
        }else{
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }


}