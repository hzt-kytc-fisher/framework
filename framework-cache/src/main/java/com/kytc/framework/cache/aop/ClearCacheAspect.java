
package com.kytc.framework.cache.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.kytc.framework.cache.redis.RedisUtil;
import com.kytc.framework.common.utils.AnalyticalParamNames;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 何志同
 * @Date: 2020/8/22 16:21
 * @Description: 缓存注解实现
**/
@Component
@Aspect
@Slf4j
public class ClearCacheAspect {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${spring.application.name}")
    private String applicationName;
    @Pointcut("@annotation(com.kytc.framework.cache.aop.ClearCache)")
    public void handle() {
    }

    @Around("handle()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("RedisCache注解只能用于方法");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method currentMethod = methodSignature.getMethod();
        Annotation redisCacheAnnotation = currentMethod.getAnnotation(ClearCache.class);
        ClearCache clearCache = (ClearCache) redisCacheAnnotation;
        String key = getKey(clearCache,point,methodSignature);
        try {
            Object returnData = point.proceed();
            redisUtil.del(key);
            return returnData;
        }catch (Exception e){
            throw  e;
        }
    }

    private String getKey(ClearCache clearCache,ProceedingJoinPoint point,MethodSignature methodSignature){
        String key = applicationName+":"+clearCache.cachePreKey()+":";
        if (!AnalyticalParamNames.isSpelEx(clearCache.key())) {
            key += clearCache.key();
        } else {

            Object[] methodArgs = point.getArgs();
            String[] params = methodSignature.getParameterNames();
            key += SpExpressionParser.getKey(clearCache.key(), params, methodArgs);
        }
        return key;
    }
}