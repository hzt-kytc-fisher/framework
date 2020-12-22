
package com.kytc.framework.cache.local;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @Author: 何志同
 * @Date: 2020/9/17 17:46
 * @Description:
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "cache.manage")
public class LocalCacheExpireConfig {
    private Map<String,Long> timeout;
}