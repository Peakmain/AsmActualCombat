package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.ext.MonitorHookMethodConfig
import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import com.peakmain.analytics.plugin.utils.NetworkFieldUtils
import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

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
    }


    @Override
    void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        String desc = owner + name + descriptor
        String qi_niu_desc = NetworkFieldUtils.OWNER_QI_NIU_HTTP_USER_AGENT + NetworkFieldUtils.NAME_QI_NIU_HTTP_USER_AGENT + NetworkFieldUtils.DESCRIPTOR_QI_NIU_HTTP_USER_AGENT
        if (desc == qi_niu_desc) {
            super.visitMethodInsn(MethodFieldUtils.STATIC_OPCODE
                    , NetworkFieldUtils.OWNER_NEW_QI_NIU_HTTP_USER_AGENT,
                    NetworkFieldUtils.NAME_QI_NIU_HTTP_USER_AGENT, NetworkFieldUtils.DESCRIPTOR_NEW_QI_NIU_HTTP_USER_AGENT, isInterface)
        }else{
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }

    }


}