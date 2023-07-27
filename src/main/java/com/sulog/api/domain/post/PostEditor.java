package com.sulog.api.domain.post;

import lombok.Builder;
import lombok.Getter;

/**
 * 수정을 할 수 있는 필드에 대해서만 모아놓은 클래스
 */
@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
