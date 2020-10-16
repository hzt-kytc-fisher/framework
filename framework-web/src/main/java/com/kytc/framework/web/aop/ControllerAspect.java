/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.web.aop;

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 何志同
 * @Date: 2020/8/22 16:21
 * @Description: 缓存注解实现
**/
@Component
@Aspect
@Slf4j
public class ControllerAspect {
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    @Pointcut("execution(public * com.kytc..*ApiImpl.*(..)) || " +
            "execution(public * com.kytc..*Controller.*(..))")
    public void handle() {
    }

    @Around("handle()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method currentMethod = methodSignature.getMethod();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 如果有session则返回session如果没有则返回null(避免创建过多的session浪费内存)
        String traceId = request.getHeader("TRACE_ID");
        String userId = request.getHeader("userId");
        if(StringUtils.isEmpty(traceId)){
            traceId = UUID.randomUUID().toString();
        }
        MDC.put("traceId",traceId);
        MDC.put("userId",userId);
        log.info("request params>>>>>>uri={}", request.getRequestURI());
        return point.proceed();
    }
}