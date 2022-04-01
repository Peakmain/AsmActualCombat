package com.peakmain.analytics.plugin.entity;

/**
 * author ：Peakmain
 * createTime：2022/4/1
 * mail:2726449200@qq.com
 * describe：
 */
class MethodCalledBean {
    private String className;
    private int access;
    private String name;
    private String descriptor;

    MethodCalledBean(String mClassName, int access, String name, String descriptor) {
        this.className = mClassName;
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return "MethodCalledBean{" +
                "className='" + className + '\'' +
                ", access=" + access +
                ", name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                '}';
    }
}
