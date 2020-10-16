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
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private long timeLength;
    public boolean isSuccess(){
        return code == 10000;
    }

    public static <T> BaseResponse<T> success(T data){
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setData(data);
        baseResponse.setCode(10000);
        baseResponse.setMessage("");
        return baseResponse;
    }
}