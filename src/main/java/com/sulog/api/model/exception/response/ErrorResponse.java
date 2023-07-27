package com.sulog.api.model.exception.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
