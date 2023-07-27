package com.sulog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 최상위 익셉션
 */
@Getter
public abstract class SulogException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public SulogException(String message) {
        super(message);
    }

    public SulogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
