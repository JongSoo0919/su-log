package com.sulog.api.model.post.request;

import com.sulog.api.exception.InvalidException;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;

    @Builder
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if(!StringUtils.hasText(title)){
            throw new InvalidException("title", "제목은 필수 입니다.");
        }
        if(!StringUtils.hasText(content)){
            throw new InvalidException("content", "내용은 필수 입니다.");
        }
        if(title.contains("금지어")){
            throw new InvalidException("title", "제목은 금지어를 포함할 수 없습니다.");
        }

    }
}
