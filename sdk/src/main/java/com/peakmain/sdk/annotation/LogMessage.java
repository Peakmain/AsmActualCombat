package com.peakmain.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author ：Peakmain
 * createTime：2021/6/16
 * describe：
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMessage {
    /**
     * 是否打印方法耗时时间
     */
    boolean isLogTime() default false;

    /**
     *
     * 是否打印方法的参数和返回值
     */
    boolean isLogParametersReturnValue() default false;
}
