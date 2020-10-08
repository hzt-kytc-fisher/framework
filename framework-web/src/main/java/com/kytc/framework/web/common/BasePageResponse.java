/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.web.common;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @Author: 何志同
 * @Date: 2020/9/18 18:59
 * @Description:
 **/
@Data
public class BasePageResponse<T> implements Serializable {
    private List<T> rows;
    private Long total;
    private Integer page;
    private Integer pageSize;
}