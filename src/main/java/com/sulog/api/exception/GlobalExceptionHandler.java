package com.sulog.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 검증되지 않은 Data에 대한 Exception 처리
     * @param e
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> invalidRequestHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> response = new HashMap<>();
        fieldErrors.stream()
                .forEach(fieldError -> response.put(fieldError.getField(), fieldError.getDefaultMessage()));
//        FieldError fieldError = e.getFieldError();
//        response.put(fieldError.getField(), fieldError.getDefaultMessage());
        return response;
    }
}
