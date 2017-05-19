package com.aek56.microservice.auth.exception.base;

/**
 * 业务异常.
 *
 * @author zj@aek56.com
 */
public class BusinessException extends Exception {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}