package com.sulog.api.controller.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.exception.InvalidException;
import com.sulog.api.model.post.PostEdit;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.model.post.request.PostSearchRequestDto;
import com.sulog.api.model.post.response.PostResponseDto;
import com.sulog.api.model.post.response.PostResponseRssDto;
import com.sulog.api.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts/get-test")
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
        PostResponseDto postResponseDto = new PostResponseDto(postService.getOne(postId));

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

    @GetMapping("/posts")
    public List<PostResponseDto> getAll(
//            @RequestParam(name = "page", required = false) Integer page
//            Pageable page
            @ModelAttribute PostSearchRequestDto postSearchRequestDto
            ){
        /**
//         * 페이지 객체를 가져오지 않는 이상, 전체를 반환하도록 설계
//         * 미 동작으로재구현
//         */
//        if(Objects.isNull(postSearchRequestDto)){
//            return postService.getAll().stream()
//                    .map(PostResponseDto::new)
//                    .collect(Collectors.toList());
//        }

        return postService.getPostsByPaging(postSearchRequestDto).stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> write(
            @RequestBody @Valid PostRequestDto params
            ) throws Exception{
        log.info("params : {} ", params.toString());
        params.validate(); // TODO : aop로 메서드 실행마다 체크
        Post post = postService.write(params);

        return ResponseEntity.ok(post.getId());
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> edit(
            @PathVariable Long postId,
            @RequestBody @Valid PostEdit postEdit,
            HttpServletRequest request
            ) throws Exception{
        log.info("postEdit : {} ", postEdit.toString());

        PostResponseDto postResponseDto = new PostResponseDto(postService.edit(postId, postEdit));

        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/posts/{postId}")
    public void edit(
            @PathVariable Long postId,
            HttpServletRequest request
    ) throws Exception{
        postService.deleteById(postId);
    }

}
