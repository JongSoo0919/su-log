package com.sulog.api.model.post.request;

import com.sulog.api.exception.InvalidException;
import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "타이틀을 입력해주세요.") private String title;
    @NotBlank(message = "컨텐츠를 입력해주세요.") private String content;

    @Builder
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if(title.contains("금지어")){
            throw new InvalidException("title", "제목은 금지어를 포함할 수 없습니다.");
        }

    }
}
