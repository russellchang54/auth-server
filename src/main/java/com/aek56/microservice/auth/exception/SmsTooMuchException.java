package com.aek56.microservice.auth.exception;


import com.aek56.microservice.auth.exception.base.BusinessException;

/**
 * 短信发送太频繁
 *
 * @author zj@aek56.com
 */
public class SmsTooMuchException extends BusinessException {

    public SmsTooMuchException(String message) {
        super(message);
    }

}
