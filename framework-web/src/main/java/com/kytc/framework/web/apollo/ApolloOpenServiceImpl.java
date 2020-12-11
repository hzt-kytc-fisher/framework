package com.kytc.framework.web.apollo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.*;

@Service
public class ApolloOpenServiceImpl {
 
    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;
    @Value("${app.apollo.service.test.appId:spring-cloud-public}")
    private String serviceTestTempAppId;
 
    private String operator = "apollo";
 
 
    /**
     * @param env
     * @param clusterName
     * @param namespaceName
     * @Description 获取指定namespace下的所有信息接口
     * @Author: 
     * @Date: 10:50 2020/1/15
     */
    public OpenNamespaceDTO getNamespace(String env, String clusterName, String namespaceName) {
        OpenNamespaceDTO namespace = apolloOpenApiClient.getNamespace(serviceTestTempAppId, env, clusterName,
                namespaceName);
        return namespace;
    }
 
    /**
     * @param name
     * @param format
     * @param isPublic
     * @param appendNamespacePrefix
     * @Description 新增namespace
     * @Author: 
     * @Date: 10:53 2020/1/15
     */
    public OpenAppNamespaceDTO addNameSpace(String name, String format, Boolean isPublic, Boolean appendNamespacePrefix) {
        OpenAppNamespaceDTO openAppNamespaceDTO = new OpenAppNamespaceDTO();
        openAppNamespaceDTO.setName(name);
        openAppNamespaceDTO.setAppId(serviceTestTempAppId);
        openAppNamespaceDTO.setFormat(format);
        openAppNamespaceDTO.setPublic(isPublic);
        openAppNamespaceDTO.setAppendNamespacePrefix(appendNamespacePrefix);
        openAppNamespaceDTO.setDataChangeCreatedBy(operator);
        OpenAppNamespaceDTO appNamespace = apolloOpenApiClient.createAppNamespace(openAppNamespaceDTO);
        return appNamespace;
    }
 
    /**
     * @param key
     * @param value
     * @param env
     * @param clusterName
     * @param namespaceName
     * @Description 新增配置
     * @Author: 
     * @Date: 10:57 2020/1/15
     */
    public OpenItemDTO createItem(String key, String value, String env, String clusterName, String namespaceName) {
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(key);
        openItemDTO.setValue(value);
        openItemDTO.setDataChangeCreatedBy(operator);
        OpenItemDTO item = apolloOpenApiClient.createItem(serviceTestTempAppId, env, clusterName, namespaceName,
                openItemDTO);
        return item;
    }
 
    /**
     * @param key
     * @param value
     * @param env
     * @param clusterName
     * @param namespaceName
     * @Description key存在就更新，不存在就添加
     * @Author: 
     * @Date: 10:59 2020/1/15
     */
    public String createOrUpdateItem(String key, String value, String env, String clusterName, String namespaceName) {
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(key);
        openItemDTO.setValue(value);
        openItemDTO.setDataChangeCreatedBy(operator);
        apolloOpenApiClient.createOrUpdateItem(serviceTestTempAppId,env,clusterName,namespaceName,openItemDTO);
        return "success";
    }
 
    /**
     * @param key
     * @param env
     * @param clusterName
     * @param namespaceName
     * @Description 删除配置
     * @Author: 
     * @Date: 11:00 2020/1/15
     */
    public String removeItem(String key, String env, String clusterName, String namespaceName) {
        apolloOpenApiClient.removeItem(serviceTestTempAppId,env,clusterName,namespaceName,key,operator);
        return "success";
    }
 
    /**
     * @param releaseTitle
     * @param releaseComment
     * @param env
     * @param clusterName
     * @param namespaceName
     * @Description 发布指定namespace下所有配置
     * @Author: 
     * @Date: 11:02 2020/1/15
     */
    public OpenReleaseDTO publishNamespace(String releaseTitle, String releaseComment, String env, String clusterName,
                                   String namespaceName) {
        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setReleaseTitle(releaseTitle);
        namespaceReleaseDTO.setReleaseComment(releaseComment);
        namespaceReleaseDTO.setReleasedBy(operator);
        OpenReleaseDTO openReleaseDTO = apolloOpenApiClient.publishNamespace(serviceTestTempAppId, env, clusterName,
                namespaceName, namespaceReleaseDTO);
        return openReleaseDTO;
    }
 
    /**
     * @param env
     * @param clusterName
     * @param namespaceName
     * @Description 获取指定namespace下所有已经生效的已发布配置
     * @Author: 
     * @Date: 11:04 2020/1/15
     */
    public OpenReleaseDTO getLatestActiveRelease(String env, String clusterName, String namespaceName) {
        OpenReleaseDTO latestActiveRelease = apolloOpenApiClient.getLatestActiveRelease(serviceTestTempAppId, env,
                clusterName, namespaceName);
        return latestActiveRelease;
    }
}