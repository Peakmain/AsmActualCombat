package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.entity.MethodCalledBean
import com.peakmain.analytics.plugin.ext.MonitorConfig
import com.peakmain.analytics.plugin.ext.MonitorHookMethodConfig
import com.peakmain.analytics.plugin.utils.MethodFieldUtils
import com.peakmain.analytics.plugin.utils.OpcodesUtils
import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type

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
    private String methodDesc
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
        methodDesc = desc
    }


    @Override
    void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
        HashMap<String, MethodCalledBean> methodReplaceBeans = MonitorHookMethodConfig.methodCalledBeans
        String desc = owner + name + descriptor
        String replacefm = "com/loc/fm" + "a" +
                "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Class;)Ljava/lang/Object;"
        String replace = "com/loc/x" + "a" +
                "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"
        if (mClassName.contains("com/loc") && (replace == desc)) {

            //println("mClassName:" + mClassName + "调用反射的owner:" + owner + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            super.visitMethodInsn(MethodFieldUtils.STATIC_OPCODE,
                    MethodFieldUtils.NEW_METHOD_REFLEX_OWNER,
                    "a",
                    "（Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;",
                    false)
        }else if(mClassName.contains("com/loc")&&replacefm==desc){
            //println("mClassName:" + mClassName + "调用反射的owner:" + owner + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            super.visitMethodInsn(MethodFieldUtils.STATIC_OPCODE,
                    MethodFieldUtils.NEW_METHOD_REFLEX_OWNER,
                    "a",
                    "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Class;)Ljava/lang/Object;",
                    false)
        }

        else if (!monitorConfig.whiteList.contains(mClassName) &&
                !monitorConfig.exceptSet.contains(mClassName) &&
                methodReplaceBeans.containsKey(desc)
                && (mClassName.contains('cn/jiguang') ||
                mClassName.contains('libcore/util/Jauns') ||
                mClassName.contains("com/alipay") ||
                mClassName.contains("com/amap") ||
                mClassName.contains("com/loc") || mClassName.contains("cn/sharesdk")
                || mClassName.contains("com/tencent") ||
                mClassName.contains("com/peakmain"))) {
            // println("调用方法的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
            MethodCalledBean bean = methodReplaceBeans.get(desc)
            super.visitMethodInsn(bean.newOpcode, bean.newMethodOwner, bean.newMethodName, bean.newMethodDescriptor.get(descriptor), false)
        } else {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
/*       if(owner=='okhttp3/Request$Builder'){
           if(name=="addHeader"){
               println("调用方法的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
           }
       }
        if(owner=="com/mob/tools/network/NetworkHelper"){
            println("调用mob网络方法的class:" + mClassName + ",方法的名字:" + name + ",方法的描述符：" + descriptor)
        }*/
    }


}