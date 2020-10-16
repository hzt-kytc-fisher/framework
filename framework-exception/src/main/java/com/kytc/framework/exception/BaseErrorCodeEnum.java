/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.exception;

/**
 * @Author: 何志同
 * @Date: 2020/9/28 9:40
 * @Description:
 **/
public enum BaseErrorCodeEnum implements BaseErrorCode{
    SUCCESS(10000,"操作成功"),
    SYSTEM_ERROR(20000,"系统错误"),
    OPERATION_FAILED(30000,"操作失败"),
    HTTP_REQUEST_FAILED(40000,"HTTP请求失败"),
    DATA_NOT_FOUND(50000,"数据不存在"),
    DATA_HAS_EXISTS(60000,"数据已经存在"),
    AUTH_FAILED(70000,"用户权限不足"),
    ERROR(99999,"操作失败"),
    ;
    private int code;
    private String desc;
    BaseErrorCodeEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}