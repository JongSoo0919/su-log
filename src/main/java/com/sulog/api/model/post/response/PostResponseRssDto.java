package com.sulog.api.model.post.response;

import com.sulog.api.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseRssDto {
    private Long id;
    private String title;
    private String content;

    @Builder
    public PostResponseRssDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    /**
     * RSS는 전체 타이틀 중 10 글자만 나가야한다, 서비스 정책
     * @param post
     * @return
     */
    public static PostResponseRssDto of(Post post){
        PostResponseRssDto postResponseDto = PostResponseRssDto.builder()
                .id(post.getId())
                .title(post.getTitle().substring(0, Math.min(post.getTitle().length(), 10)))
                .content(post.getContent())
                .build();

        return postResponseDto;
    }
}
