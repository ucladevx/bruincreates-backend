package com.bruincreates.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse<T> {

    boolean success;
    String message;
    T data;

    public static <T> RestResponse<T> success() {
        return new RestResponse<>(true, "success", null);
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(true, "success", data);
    }

    public static <T> RestResponse<T> success(String message) {
        return new RestResponse<>(true, message, null);
    }

    public static <T> RestResponse<T> success(String message, T data) {
        return new RestResponse<>(true, message, data);
    }

    public static <T> RestResponse<T> fail() {
        return new RestResponse<>(false, "system busy", null);
    }

    public static <T> RestResponse<T> fail(T data) {
        return new RestResponse<>(false, "system busy", data);
    }

    public static <T> RestResponse<T> fail(String message) {
        return new RestResponse<>(false, message, null);
    }

    public static <T> RestResponse<T> fail(String message, T data) {
        return new RestResponse<>(false, message, data);
    }

}
