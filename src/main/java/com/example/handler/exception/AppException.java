package com.example.handler.exception;

import com.example.constant.CodeConstant;

/**
 * Created by Administrator on 2017/5/27.
 */
public class AppException extends RuntimeException {
    private int code;

    public AppException(CodeConstant codeConstant) {
        super(codeConstant.getMsg());
        this.code = codeConstant.getCode();
    }

    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
