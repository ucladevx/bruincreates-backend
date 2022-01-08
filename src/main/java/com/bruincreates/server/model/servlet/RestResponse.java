package com.bruincreates.server.model.servlet;

import lombok.Data;

/**
 * Generic Rest Response
 * @author aojiao
 */
@Data
public class RestResponse<T> extends BaseResponse {

    private T data;

    /**
     * Success without data
     */
    public static <T> RestResponse<T> success() {
        return build(ResponseCode.SUCCESS);
    }

    /**
     * Success with data
     */
    public static <T> RestResponse<T> success(T data) {
        return build(ResponseCode.SUCCESS, data);
    }

    /**
     * Unknown error, return system busy as response<br/>
     * Use with caution, try provide clearer response
     */
    public static <T> RestResponse<T> fail() {
        return build(ResponseCode.FAIL);
    }

    /**
     * Unknown error, return system busy as response<br/>
     *
     * @param data Error message
     */
    public static <T> RestResponse<T> fail(T data) {
        return build(ResponseCode.FAIL, data);
    }

    /**
     * Failure without data
     *
     * @param responseCode Failure code
     */
    public static <T> RestResponse<T> fail(ResponseCode responseCode) {
        return build(responseCode);
    }

    /**
     * Failure with data
     *
     * @param responseCode Failure code
     * @param data Failure message
     */
    public static <T> RestResponse<T> fail(ResponseCode responseCode, T data) {
        return build(responseCode, data);
    }

    /**
     * Customized Response without data
     *
     * @param responseCode Customized code
     */
    public static <T> RestResponse<T> build(ResponseCode responseCode) {
        return new RestResponse<>(responseCode.getCode(), responseCode.getMessage());
    }

    /**
     * Customized Response with data
     *
     * @param responseCode Customized code
     * @param data Customized data
     */
    public static <T> RestResponse<T> build(ResponseCode responseCode, T data) {
        return build(responseCode.getCode(), responseCode.getMessage(), data);
    }

    /**
     * Customized Response with message<br/>
     * Usually used for global exception handler @ControllerAdvice
     *
     * @param code Customized Status
     * @param message Customized Message
     */
    public static <T> RestResponse<T> build(int code, String message) {
        return new RestResponse<T>(code, message, null);
    }

    /**
     * Customized Response with message and data<br/>信息
     * Usually used for global exception handler @ControllerAdvice
     *
     * @param code Customized Status
     * @param message Customized Message
     * @param data Customized Data
     */
    public static <T> RestResponse<T> build(int code, String message, T data) {
        return new RestResponse<T>(code, message, data);
    }

    public boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode() == super.code;
    }

    private RestResponse() { }

    private RestResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private RestResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
