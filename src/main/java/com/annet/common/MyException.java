package com.annet.common;
/**
 * 自定义异常类
 */
public class MyException extends RuntimeException {
    /**
     * 异常码
     */
    private int code;

    public MyException(String message, int code) {
        super(message);
        this.code = code;
    }

    public MyException(String message) {
        super(message);
    }

    public int getCode() {
        return this.code;
    }
}
