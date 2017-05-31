package com.example.constant;

/**
 * Created by 返回码枚举类 on 2017/5/27.
 */
public enum CodeConstant {
    SUCCESS(0,"操作成功"),
    SYS_ERROR(-1,"系统异常");
    int code;
    String msg;
    CodeConstant(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
