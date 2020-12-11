/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.web.common;

import java.io.Serializable;

import lombok.Data;

/**
 * @Author: 何志同
 * @Date: 2020/9/17 19:59
 * @Description:
 **/
@Data
public class BasePageRequest implements Serializable {
    private int page;
    private int pageSize;
    private int start;
    private int limit;
    private String sortField;
    private String sort;
    public void init(){
        this.limit = pageSize;
        this.start = (this.page-1)*pageSize;
    }
}