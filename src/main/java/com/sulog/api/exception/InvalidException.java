package com.sulog.api.exception;

import lombok.Getter;

@Getter
public class InvalidException extends SulogException{
    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidException() {
        super(MESSAGE);
    }

    public InvalidException(String fieldName, String message){
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
