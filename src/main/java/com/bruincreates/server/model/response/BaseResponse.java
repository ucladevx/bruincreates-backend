package com.bruincreates.server.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    protected int code = ResponseCode.SUCCESS.getCode();
    protected String message = ResponseCode.SUCCESS.getMessage();

    public BaseResponse() { }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
