
package com.kytc.framework.cache.aop;

import java.lang.annotation.*;

/**
 * @Author: 何志同
 * @Date: 2020/8/22 16:11
 * @Description: redis注解Key 清除缓存
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearCache {
    /**
     * 缓存key
     */
    String cachePreKey() default "";
    /**
     * 缓存key
     */
    String key() default "";
}