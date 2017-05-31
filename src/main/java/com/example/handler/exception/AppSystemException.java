package com.example.handler.exception;

import com.example.constant.CodeConstant;

/**
 * Created by Administrator on 2017/5/31.
 */
public class AppSystemException extends AppException {
    public AppSystemException(CodeConstant codeConstant) {
        super(codeConstant);
    }

    public AppSystemException(String message, int code) {
        super(message, code);
    }
}
