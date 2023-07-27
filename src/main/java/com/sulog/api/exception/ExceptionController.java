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
        ErrorResponse response =  ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        e.getFieldErrors().stream()
                        .forEach(fieldError -> response.addValidation(fieldError.getField(), fieldError.getDefaultMessage()));
        return response;
    }

    @ExceptionHandler(SulogException.class)
    public ResponseEntity<ErrorResponse> sulogExceptionHandler(SulogException e){
        log.error(e.getMessage());
        ErrorResponse response =  ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(e.getStatusCode())
                .body(response);
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(PostNotFoundException.class)
//    public ErrorResponse postNotFound(PostNotFoundException e){
//        log.error(e.getMessage());
//        ErrorResponse response =  ErrorResponse.builder()
//                .code("404")
//                .message(e.getMessage())
//                .build();
//
//        return response;
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(InvalidException.class)
//    public ErrorResponse postNotFound(InvalidException e){
//        log.error(e.getMessage());
//        ErrorResponse response =  ErrorResponse.builder()
//                .code("400")
//                .message(e.getMessage())
//                .build();
//
//        return response;
//    }
}
