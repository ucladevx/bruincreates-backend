package com.bruincreates.server.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BadRequestException extends Exception{
    public String message;
}
