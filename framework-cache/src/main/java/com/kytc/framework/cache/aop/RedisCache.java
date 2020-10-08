/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.cache.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 何志同
 * @Date: 2020/8/22 16:11
 * @Description: redis注解Key 方便加缓存
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {
    /**
     * 缓存key
     */
    String cachePreKey() default "";
    /**
     * 缓存key
     */
    String key() default "";
    /**
     * 缓存有效期
    **/
    int expireTime() default 5;
    /**
     * 缓存时间单位
     **/
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}