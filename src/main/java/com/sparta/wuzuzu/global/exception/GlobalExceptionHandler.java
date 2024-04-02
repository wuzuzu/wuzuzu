package com.sparta.wuzuzu.global.exception;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.common.dto.ExceptionResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
            .msg(ex.getMessage())
            .httpCode(HttpStatus.BAD_REQUEST.value())
            .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ValidateUserException.class})
    public ResponseEntity<ExceptionResponse> ValidateException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
            .msg(ex.getMessage())
            .httpCode(HttpStatus.FORBIDDEN.value())
            .build();
        return ResponseEntity.badRequest().body(response);
    }
}