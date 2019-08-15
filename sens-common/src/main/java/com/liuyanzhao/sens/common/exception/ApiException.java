package com.liuyanzhao.sens.common.exception;

/**
 * @author 言曌
 * @date 2019-08-09 16:47
 */

public class ApiException extends RuntimeException {

    private Integer code;

    private String message;


    public ApiException() {
        super();
    }

    public ApiException(String message) {
        this.code = 500;
        this.message = message;
    }

    public ApiException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
