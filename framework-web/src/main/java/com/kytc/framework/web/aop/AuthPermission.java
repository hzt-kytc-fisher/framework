package com.kytc.framework.web.aop;

import java.lang.annotation.*;

/**
 * @author hezhitong
 * @date 2020/10/2018:40
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPermission {
    String[] hasPermission();

    String[] hasRole();
}
