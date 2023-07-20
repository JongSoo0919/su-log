package com.sulog.api.controller.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts")
    public String get(){
        return "Hello World!";
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> post(
            @RequestBody @Valid PostRequestDto params
            ) throws Exception{
        log.info("params : {} ", params.toString());

        Post post = postService.write(params);

        return ResponseEntity.ok(post.getId());
    }
}
