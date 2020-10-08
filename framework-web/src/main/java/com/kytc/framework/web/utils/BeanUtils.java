/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.web.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 何志同
 * @Date: 2020/9/18 19:19
 * @Description:
 **/
public class BeanUtils {
    public static <T>T convert(Object obj,Class<T> clazz){
        if( null == obj ){
            return null;
        }
        try {
            T t = clazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(obj,t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> convert(List<? extends Object> objs, Class<T> clazz){
        if(CollectionUtils.isEmpty(objs)){
            return null;
        }
        List<T> list = new ArrayList<>();
        for(Object obj:objs){
            list.add(convert(obj,clazz));
        }
        return list;
    }
}