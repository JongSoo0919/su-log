package com.sulog.api.controller;

import com.sulog.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class PostController {
    @GetMapping("/posts")
    public String get(){
        return "Hello World!";
    }

    @PostMapping("/posts")
    public String post(
            // 1. 각자 단일 파라미터로 받는 방법
//            @RequestParam String title,
//            @RequestParam String content
            // 2. Map을 통해서 한번에 받아오는 방법
//            @RequestParam Map<String, String> params
            // 3. DTO 객체를 통해서 각 객체에 매핑시키는 방법 Setter 필수
            @RequestBody PostCreate params
            ){
//        log.info("title : {}, content {}",title, content);
        log.info("params : {} ", params.toString());
        return "Hello World";
    }
}
