package com.sulog.api.controller;

import com.sulog.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PostController {
    @GetMapping("/posts")
    public String get(){
        return "Hello World!";
    }

    @PostMapping("/posts")
    public Map<String, String> post(
            @RequestBody @Valid PostCreate params,
            BindingResult result
            ) throws Exception{
        log.info("params : {} ", params.toString());

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // error 메세지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of("title", "");
    }
}
