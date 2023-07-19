package com.sulog.api.model.exception.response;

//TODO : enum으로 에러 객체 처리
public enum ErrorEnum {
    VALID_ERROR(400, "파라미터 검증에 실패하였습니다."),
    FORBIDDEN_ERROR(403, "권한 검증에 실패하였습니다.");

    private final int code;
    private final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
