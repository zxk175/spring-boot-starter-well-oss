package com.zxk175.oss.exception;

/**
 * @author zxk175
 * @since 2020-03-24 10:48
 */
public class OssException extends RuntimeException {

    public OssException(String message) {
        super(message);
    }

    public OssException(String msg, Throwable e) {
        super(msg, e);
    }

}
