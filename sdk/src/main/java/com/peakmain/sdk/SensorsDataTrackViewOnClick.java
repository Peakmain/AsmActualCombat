package com.peakmain.sdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:Peakmain
 * createTime:2021/6/15
 * mail:2726449200@qq.com
 * describe：
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensorsDataTrackViewOnClick {
}
