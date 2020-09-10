package com.annet.result;

import java.io.Serializable;

/**
 * Created by jiangweiyang on 16/11/10.
 */
public class ResultSupport implements Serializable {
    private static final long serialVersionUID = -2235152751651905167L;

    public boolean isSuccess() {
        return code == ResultCode.SuccessCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String message;
    private int code;
}
