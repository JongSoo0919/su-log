package com.sulog.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {
    @NotBlank private String title;
    @NotBlank private String content;
}
