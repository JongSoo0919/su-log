package com.sulog.api.model.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Builder
public class PostSearchRequestDto {
    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

//    @Builder
//    public PostSearchRequestDto(Integer page, Integer size) {
//        this.page = page;
//        this.size = size;
//    }

    public long getOffset(){
        return (long) (Math.max(page, 1) - 1) * Math.min(size, MAX_SIZE);
    }
}
