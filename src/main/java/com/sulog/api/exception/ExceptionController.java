package com.sulog.api.exception;

import com.sulog.api.model.exception.response.ErrorEnum;
import com.sulog.api.model.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * 검증되지 않은 Data에 대한 Exception 처리
     * TODO: enum 처리
     *
     * @param e
     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorEnum> invalidRequestHandler(MethodArgumentNotValidException e){
//        log.error(e.getMessage());
//        return ResponseEntity.badRequest().body(ErrorEnum.VALID_ERROR);
//    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        ErrorResponse response =  new ErrorResponse("400", "잘못된 요청입니다.");
        e.getFieldErrors().stream()
                        .forEach(fieldError -> response.addValidation(fieldError.getField(), fieldError.getDefaultMessage()));
        return response;
    }
}
