package com.kytc.framework.web.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author weiwenbin
 * @Date 2020/5/14 下午5:31
 */
@Component
public class AnnotationUtil {
    @Autowired
    private ResourceLoader resourceLoader;

    private static final String VALUE = "value";

    /**
     * 获取指定包下所有添加了执行注解的方法信息
     * @param classPath 包名
     * @param <T>
     * @return
     * @throws Exception
     */
    public  <T> Map<String, Map<String, Object>> getAllAddTagAnnotationUrl(String classPath,Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> resMap = new HashMap<>();
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resolver.getResources(classPath);

        for (org.springframework.core.io.Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader(r);
            resMap = resolveClass(reader, resMap,clazz);
        }
        return resMap;
    }

    private <T> Map<String, Map<String, Object>> resolveClass( MetadataReader reader, Map<String, Map<String, Object>> resMap ,Class<T> clazz)
            throws Exception {
        //获取注解元数据
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        //获取类中RequestMapping注解的属性
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(RequestMapping.class.getCanonicalName());

        //若类无RequestMapping注解
        if (annotationAttributes == null) {
            return resMap;
        }

        //获取RequestMapping注解的value
        String[] pathParents = (String[]) annotationAttributes.get(VALUE);
        if (0 == pathParents.length) {
            return resMap;
        }

        //获取RequestMapping注解的value
        String pathParent = pathParents[0];

        //获取当前类中已添加要扫描注解的方法
        Set<MethodMetadata> annotatedMethods = annotationMetadata.getAnnotatedMethods(clazz.getCanonicalName());

        for (MethodMetadata annotatedMethod : annotatedMethods) {
            //获取当前方法中要扫描注解的属性
            Map<String, Object> targetAttr = annotatedMethod.getAnnotationAttributes(clazz.getCanonicalName());
            //获取当前方法中要xxxMapping注解的属性
            Map<String, Object> mappingAttr = getPathByMethod(annotatedMethod);
            if (mappingAttr == null){
                continue;
            }

            String[] childPath = (String[]) mappingAttr.get(VALUE);
            if (targetAttr == null || childPath == null || childPath.length == 0) {
                continue;
            }
            String request = (String)mappingAttr.get("requestType");
            String path = pathParent + childPath[0];

            boolean isHas = resMap.containsKey(request+":"+path);
            if (isHas){
                throw new Exception("重复定义了相同的映射关系");
            }

            resMap.put(request+":"+path, targetAttr);
        }

        return resMap;
    }


    private Map<String, Object> getPathByMethod(MethodMetadata annotatedMethod) {
        Map<String, Object> annotationAttributes = annotatedMethod.getAnnotationAttributes(GetMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            annotationAttributes.put("requestType","GET");
            return annotationAttributes;
        }
        annotationAttributes = annotatedMethod.getAnnotationAttributes(PostMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            annotationAttributes.put("requestType","POST");
            return annotationAttributes;
        }

        annotationAttributes = annotatedMethod.getAnnotationAttributes(DeleteMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            annotationAttributes.put("requestType","DELETE");
            return annotationAttributes;
        }

        annotationAttributes = annotatedMethod.getAnnotationAttributes(PutMapping.class.getCanonicalName());
        if (annotationAttributes != null && annotationAttributes.get(VALUE) != null) {
            annotationAttributes.put("requestType","PUT");
            return annotationAttributes;
        }
        annotationAttributes = annotatedMethod.getAnnotationAttributes(RequestMapping.class.getCanonicalName());
        annotationAttributes.put("requestType","REQUEST");
        return annotationAttributes;
    }
}