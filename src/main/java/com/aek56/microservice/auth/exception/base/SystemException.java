package com.aek56.microservice.auth.exception.base;

/**
 * 系统业务异常.
 *
 * @author zj@aek56.com
 */
public class SystemException extends RuntimeException {

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}