package com.bruincreates.server.model.servlet;

/**
 * @author aojiao
 * @date 2022/01/07
 */
public enum ResponseCode {
    /**
     * Shared Status Code
     */
    FAIL(-1, "System Busy"),
    SUCCESS(0, "Success"),

    // Random
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    GLOBAL_PARAM_ERROR(4000, "Wrong Request Parameter"),
    CURRENT_USER_FAIL(10001, "Error Retrieving Current User"),

    // Login
    USER_NEED_LOGIN(11001, "Please Login First"),
    USER_MAX_LOGIN(11002, "User Logged In Using Another Device"),
    USER_LOGIN_TIMEOUT(11003, "User Session Timeout"),
    USER_DISABLED(11004, "User Temporarily Disabled"),
    USER_PASSWORD_ERROR(11006, "Username or Password Incorrect"),
    USER_NOT_EXIST(11009, "Username or Password Incorrect"),
    USER_LOGIN_FAIL(11010, "Login Failed"),
    VERIFY_CODE_ERROR(11011,"Wrong Verification Code"),
    USER_IS_EXIST(11012, "User Already Exists"),
    NO_AUTHORIZATION(1003006, "Not Authorized"),

    //File System
    FILE_NOT_EXIST(16001, "File Not Found"),
    FILE_UPLOAD_EXCEPTION(16002, "Error Uploading File"),
    FILE_DOWNLOAD_ABNORMAL(16003, "Error Downloading File"),
    FILE_DELETE_FAIL(16004, "Error Deleting File"),

    //Resources
    RESOURCE_NOT_FIND(12001, "Invalid Resource Id"),
    RESOURCE_IS_EXIST(12001, "Resource Id Existed"),

    //Request
    BAD_REQUEST(13001, "Bad Request");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}