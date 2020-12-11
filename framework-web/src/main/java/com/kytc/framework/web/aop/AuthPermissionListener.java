package com.kytc.framework.web.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kytc.framework.exception.BaseErrorCodeEnum;
import com.kytc.framework.exception.BaseException;
import com.kytc.framework.web.apollo.ApolloOpenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AuthPermissionListener implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    @Autowired
    private AnnotationUtil annotationUtil;
    @Autowired
    private ApolloOpenServiceImpl apolloOpenService;
    @Value("${spring.application.name}")
    private String applicationName;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        getAnnotationMap(applicationContext);
    }

    public void getAnnotationMap(ApplicationContext applicationContext) {
        try{
            Map<String, Map<String, Object>> map = annotationUtil.getAllAddTagAnnotationUrl("classpath*:com/kytc/**/*Controller.class",AuthPermission.class);
            apolloOpenService.createOrUpdateItem("authMap."+applicationName, JSON.toJSONString(map),"DEV","DEFAULT","TEST1.auth");
            apolloOpenService.publishNamespace("发布","发布","DEV","DEFAULT","TEST1.auth");
        }catch (Exception e){

        }
    }
}