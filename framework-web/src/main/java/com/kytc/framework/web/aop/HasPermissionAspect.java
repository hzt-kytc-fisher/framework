package com.kytc.framework.web.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.kytc.framework.exception.BaseErrorCodeEnum;
import com.kytc.framework.exception.BaseException;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 动态数据源切换处理器
 * @author Louis
 * @date Oct 31, 2018
 */
@Aspect
@Order(0)  // 该切面应当先于 @Transactional 执行
@Component
public class HasPermissionAspect {
    
    /**
     * 切换数据源
     * @param point
     * @param authPermission
     */
    @Before("@annotation(authPermission))")
    public void switchDataSource(JoinPoint point, AuthPermission authPermission) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMapping clazzRequestMapping = getClassAnnotation(point,RequestMapping.class);
        StringBuffer stringBuffer = new StringBuffer();
        if( null != clazzRequestMapping ){
            stringBuffer.append(clazzRequestMapping.value());
        }
        if(stringBuffer.toString().endsWith("/")){
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        String methodUrl[] = new String[]{};
        List<String> requestTypes = Lists.newArrayList();
        if( null != getMapping ){
            methodUrl = getMapping.value();
            requestTypes = Arrays.asList("GET");
        }else if( null != postMapping ){
            methodUrl = postMapping.value();
            requestTypes = Arrays.asList("POST");
        }else if( null != putMapping){
            methodUrl = putMapping.value();
            requestTypes = Arrays.asList("PUT");
        }else if( null != deleteMapping ){
            methodUrl = deleteMapping.value();
            requestTypes = Arrays.asList("DELETE");
        }else if( null != requestMapping ){
            methodUrl = requestMapping.value();
            if(null != requestMapping){
                for(RequestMethod requestMethod:requestMapping.method()){
                    requestTypes.add(requestMethod.name());
                }
            }else{
                for(RequestMethod requestMethod:RequestMethod.values()){
                    requestTypes.add(requestMethod.name());
                }
            }
        }else{
            throw new BaseException(BaseErrorCodeEnum.HTTP_REQUEST_FAILED,"不支持该请求方式");
        }
        if(methodUrl.length>0){
            for(String mUrl:methodUrl){
                if(StringUtils.isEmpty(mUrl)&&!mUrl.startsWith("/")){
                    mUrl = "/"+mUrl;
                }
                String url = stringBuffer.toString()+mUrl;
                for(String requestType:requestTypes){

                }
            }
        }
    }

    private <T extends Annotation> T getClassAnnotation(JoinPoint point, Class<T> clazz){
        for (Class<?> cls : point.getClass().getInterfaces()) {
            T t = cls.getAnnotation(clazz);
            if (t != null) {
                return t;
            }
        }
        return null;
    }
}