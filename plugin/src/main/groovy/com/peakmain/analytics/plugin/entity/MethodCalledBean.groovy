package com.peakmain.analytics.plugin.entity

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class MethodCalledBean {
    String className
    private int access
    String name
    String[] descriptor

    MethodCalledBean(String mClassName, String name, String[] descriptor) {
        this.className = mClassName
        this.name = name
        this.descriptor = descriptor
    }
    @Override
    String toString() {
        return "MethodCalledBean{" +
                "className='" + className + '\'' +
                ", access=" + access +
                ", name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                '}'
    }
}
