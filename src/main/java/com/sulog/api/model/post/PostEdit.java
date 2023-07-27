package com.sulog.api.model.post;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * Post Entity 수정용 객체
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostEdit {
//    @NotBlank(message = "타이틀을 입력하세요")
    private String title;
//    @NotBlank(message = "콘텐츠를 입력하세요")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
