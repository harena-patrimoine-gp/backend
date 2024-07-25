package com.harena.com.model.exception;

public class TooManyRequestsException extends ApiException{
    public TooManyRequestsException(String message) {
        super(ExceptionType.CLIENT_EXCEPTION, message);
    }
}
