package com.aek56.microservice.auth.exception;


import com.aek56.microservice.auth.exception.base.BusinessException;

/**
 * 无效验证码
 *
 * @author zj@aek56.com
 */
public class InvalidCaptchaException extends BusinessException {

    public InvalidCaptchaException(String message) {
        super(message);
    }

}
