package com.liuxinchi.mumu_mall.exception;

public class MumuMallException extends Exception{

    private final Integer code;
    private final String message;

    public MumuMallException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public MumuMallException(MumuMallExceptionEnum ex){
        this(ex.getCode(), ex.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
