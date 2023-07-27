package com.sulog.api.exception;

public class PostNotFoundException extends RuntimeException{

    private static final String MESSAGE = "존재하지 않는 글 입니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }
}
