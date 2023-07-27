package com.sulog.api.exception;

public class PostNotFoundException extends SulogException{

    private static final String MESSAGE = "존재하지 않는 글 입니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
