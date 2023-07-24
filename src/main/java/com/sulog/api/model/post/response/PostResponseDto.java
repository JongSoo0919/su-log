package com.sulog.api.model.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PostResponseDto {
    private String title;
    private String content;

    @Builder
    public PostResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
