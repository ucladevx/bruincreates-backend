package com.bruincreates.server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BadRequestException extends Exception{

    public String message;

}
