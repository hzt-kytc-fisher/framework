/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.exception;

/**
 *@Author: 何志同
 *@Date: 2020/9/28
 *@Description:
 **/
public interface BaseErrorCode {
    int getCode();

    String getDesc();
}