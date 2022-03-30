package com.peakmain.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author ：Peakmain
 * createTime：2022/3/30
 * mail:2726449200@qq.com
 * describe：打印信息注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogParametersReturnValue {
}
