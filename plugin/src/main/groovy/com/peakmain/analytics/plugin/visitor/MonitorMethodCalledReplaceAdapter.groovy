package com.peakmain.analytics.plugin.visitor

import com.peakmain.analytics.plugin.visitor.base.MonitorDefalutMethodAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

/**
 * author ：Peakmain
 * createTime：2022/4/2
 * mail:2726449200@qq.com
 * describe：
 */
class MonitorMethodCalledReplaceAdapter extends MonitorDefalutMethodAdapter {
    private String mMethodOwner = "android/telephony/TelephonyManager"
    private String mMethodName = "getDeviceId"
    private String mMethodDesc = "()Ljava/lang/String;"
    private String mMethodDesc1 = "(I)Ljava/lang/String;"

    private final int newOpcode = INVOKESTATIC
    private final String newOwner = "com/peakmain/sdk/utils/ReplaceMethodUtils"
    private final String newMethodName = "getDeviceId"
    private int mAccess
    private ClassVisitor classVisitor
    private String newMethodDesc = "(Landroid/telephony/TelephonyManager;)Ljava/lang/String;"
    private String newMethodDesc1 = "(Landroid/telephony/TelephonyManager;I)Ljava/lang/String;"

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
        if (mMethodOwner == owner && name == mMethodName) {
            if(descriptor == mMethodDesc){
                super.visitMethodInsn(newOpcode,newOwner,newMethodName,newMethodDesc,false)
            }else if(mMethodDesc1 == descriptor){
                super.visitMethodInsn(newOpcode,newOwner,newMethodName,newMethodDesc1,false)
            }

        } else {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }
}