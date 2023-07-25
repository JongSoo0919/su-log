package com.sulog.api.controller.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.model.post.response.PostResponseDto;
import com.sulog.api.model.post.response.PostResponseRssDto;
import com.sulog.api.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts")
    public PostResponseRssDto get(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            HttpServletRequest request
    ){
        return PostResponseRssDto.builder()
                .title(title)
                .content(content)
                .build();

    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> getOne(
            @PathVariable Long postId,
            HttpServletRequest request
    ){
        PostResponseDto postResponseDto = PostResponseDto.of(postService.getOne(postId));

        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("/posts/{postId}/rss")
    public ResponseEntity<PostResponseRssDto> getRss(
            @PathVariable Long postId,
            HttpServletRequest request
    ){
        PostResponseRssDto postResponseRssDto = PostResponseRssDto.of(postService.getRss(postId));

        return ResponseEntity.ok(postResponseRssDto);
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
