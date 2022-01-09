package com.bruincreates.server.model.servlet;

/**
 * @author aojiao
 * @date 2022/01/07
 */
public enum ResponseCode {

    // Generic Response
    FAIL(-1, "System Busy"),
    SUCCESS(0, "Success"),

    // Request
    DUPLICATE_EMAIL(1001, "Email Already Exist"),
    DUPLICATE_USERNAME(1002, "Username Already Exist"),
    BAD_REQUEST_PARAM(1003, "Bad Request Parameter"),
    TOO_MANY_REQUESTS(1004, "Too Many Requests"),
    BAD_REQUEST(1005, "Bad Request"),

    // Login & Registration
    USER_NEED_LOGIN(2001, "Please Login First"),
    USER_ALREADY_LOGIN(2002, "User Logged In Using Another Device"),
    USER_DISABLED(2003, "User Temporarily Disabled"),
    USER_PASSWORD_ERROR(2004, "Username or Password Incorrect"),
    USER_NOT_EXIST(2005, "Username or Password Incorrect"),
    WRONG_VERIFICATION_CODE(2006, "Wrong Verification Code"),
    USER_ALREADY_EXIST(2007, "User Already Exists"),
    NOT_AUTHORIZED(2008, "Not Authorized"),
    INVALID_USER_SESSION(2009, "Invalid User Session"),

    // File System
    FILE_NOT_FOUND(3001, "File Not Found"),
    FILE_UPLOAD_ERROR(3002, "Error Uploading File"),
    FILE_DOWNLOAD_ERROR(3003, "Error Downloading File"),
    FILE_DELETE_ERROR(3004, "Error Deleting File"),

    // Resources
    RESOURCE_NOT_FIND(4001, "Invalid Resource ID"),
    RESOURCE_ID_EXIST(4002, "Resource ID Existed");

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