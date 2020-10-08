/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.http;

/**
 * @Author: 何志同
 * @Date: 2020/9/28 18:22
 * @Description:
 **/
public class GitHubConfig {
    public static final String REPOSITORY_ORG_URL = "https://api.github.com/orgs/{org}/repos";//仓库URL GET 查询组织架构下的仓库，POST添加仓库

    public static final String REPOSITORY_OWNER_URL = "https://api.github.com/repos/{org}/{repo}";//GET 查询ORG下的仓库  PATCH 更新  DELETE删除仓库
}