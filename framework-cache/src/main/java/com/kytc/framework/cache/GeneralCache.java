/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: 何志同
 * @Date: 2020/9/17 13:24
 * @Description:
 **/
public abstract class GeneralCache<K extends Serializable,V extends Serializable> {
    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private RedisTemplate<K,V> redisTemplate;
    public V get(K k){
        String key = getKey(k);
        V v = redisTemplate.opsForValue().get(k);
        if( null != v ){
            return v;
        }
        v = getObject(k);
        if( null != v ){
            redisTemplate.opsForValue().set(k,v,getExpireTime(),getTimeUnit());
        }
        return v;
    }
    public abstract V getObject(K k);
    private String getKey(K k){
        return applicationName+this.getPrefixKey() + ":" +k;
    }
    protected abstract String getPrefixKey();
    protected TimeUnit getTimeUnit(){
        return TimeUnit.MINUTES;
    }
    protected Integer getExpireTime(){
        return 5;
    }
}