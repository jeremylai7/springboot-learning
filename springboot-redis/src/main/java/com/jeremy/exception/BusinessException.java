package com.jeremy.exception;


/**
 * 业务异常定义
 */
public class BusinessException extends Exception {

    private String code;
    private String[] args;

    public BusinessException(String code, String... args) {
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
