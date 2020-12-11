package com.kytc.framework.web.apollo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;

@Configuration
public class ApolloClientConfig {
    @Value("http://localhost:8070")   //apollo配置中心的portalUrl
    private String portalUrl;
    @Value("${app.apollo.service.bizManagementSystem.token:792b74664126a7d7ddbd2d9be49ede924190d8e4}") //授权token
    private String token;
 
    @Bean
    public ApolloOpenApiClient creatApolloOpenApiClient(){
        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl(portalUrl)
                .withToken(token).build();
        return client;
    }
}