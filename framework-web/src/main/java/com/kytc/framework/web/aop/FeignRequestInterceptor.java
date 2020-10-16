package com.kytc.framework.web.aop;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    @Autowired
    HttpServletRequest httpServletRequest;
 
    public FeignRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes)
                               RequestContextHolder.getRequestAttributes()).getRequest();
        String traceId = MDC.get("traceId");
        requestTemplate.header("TRACE_ID", traceId);
    }
}