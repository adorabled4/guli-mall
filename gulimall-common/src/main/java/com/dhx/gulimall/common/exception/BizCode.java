package com.dhx.gulimall.common.exception;

/**
 * @author dhx_
 * @className BizCode
 * @date : 2023/01/13/ 15:54
 **/
public enum BizCode {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败");

    private int code;
    private String message;

    BizCode(int code,String message ){
        this.code=code;
        this.message=message;
    }

    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

}
