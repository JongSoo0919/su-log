package com.sulog.api.model.post.response;

import com.sulog.api.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;

    /**
     * Of 보다는 생성자 오버로딩이 나은듯
     * @param post
     */
    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    /**
     * 공통 ResponseDto는 전체 타이틀이 다 나감, 서비스 정책
     * @param post
     * @return
     */
    public static PostResponseDto of(Post post){
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return postResponseDto;
    }
}
