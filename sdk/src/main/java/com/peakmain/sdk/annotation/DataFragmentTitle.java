package com.peakmain.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author ：Peakmain
 * createTime：2022/7/23
 * mail:2726449200@qq.com
 * describe：通过注解自定义 Fragment 的标题（title 属性）
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFragmentTitle {
    String title() default "";
}
