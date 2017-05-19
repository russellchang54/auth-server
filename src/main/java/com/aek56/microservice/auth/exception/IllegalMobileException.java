package com.aek56.microservice.auth.exception;


import com.aek56.microservice.auth.exception.base.BusinessException;

/**
 * 手机号码不合法
 *
 * @author zj@aek56.com
 */
public class IllegalMobileException extends BusinessException {

    public IllegalMobileException(String message) {
        super(message);
    }

}
