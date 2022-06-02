package com.jeremy.exception;

/**
 * @author Austen
 * @create 2018-04-04 9:12
 * @desc 返回结果
 **/
//统一返回对象
public class Result {
    private String code;
    private String message;
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(final Object data) {
        this.data = data;
    }
}
