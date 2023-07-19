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
            @RequestBody @Valid PostCreate params
            ) throws Exception{
        log.info("params : {} ", params.toString());

        return Map.of("title", "");
    }
}
