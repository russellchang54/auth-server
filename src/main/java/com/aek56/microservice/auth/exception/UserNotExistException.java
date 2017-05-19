package com.aek56.microservice.auth.exception;


import com.aek56.microservice.auth.exception.base.BusinessException;

/**
 * 用户未存在
 *
 * @author zj@aek56.com
 */
public class UserNotExistException extends BusinessException {

    public UserNotExistException(String message) {
        super(message);
    }

}
