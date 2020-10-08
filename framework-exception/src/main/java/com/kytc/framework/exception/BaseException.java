/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.exception;

import java.io.Serializable;

/**
 * @Author: 何志同
 * @Date: 2020/9/28 9:35
 * @Description:
 **/
public class BaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2728936692069322518L;
    private BaseErrorCode errorCode;
    private String errorMessage;

    public BaseException() {
        super("系统异常");
        this.errorCode = BaseErrorCodeEnum.OPERATION_FAILED;
        this.errorMessage = this.errorCode.getDesc();
    }

    public BaseException(BaseErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDesc();
    }

    public BaseException(BaseErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseException(BaseErrorCode errorCode, String errorMessage, Throwable exception) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        super.initCause(exception);
    }

    public BaseException(BaseErrorCode errorCode, Throwable exception) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDesc();
        super.initCause(exception);
    }

    public BaseErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorCode(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static boolean isBaseException(Throwable exception) {
        return exception instanceof BaseException;
    }
}