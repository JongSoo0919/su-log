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
            ) throws Exception{
        log.info("params : {} ", params.toString());

        String title = params.getTitle();
        if(title == null || "".equals(title)){
            // 해당 형태의 검증은 반복 및 누락이 계속해서 발생 가능.
            // * 세 번 이상 반복 작업이 있다면 잘못 설계하는 것이 아닌지 의심.
            // ""만이 아닌, "              ", "수십 억 글자 ~~~" 와 같은 검증 사항이 너무 많음.
            throw new Exception("Empty Title!");
        }

        String content = params.getContent();
        if(content == null || "".equals(content)){
            //error
        }
        return "Hello World";
    }
}
