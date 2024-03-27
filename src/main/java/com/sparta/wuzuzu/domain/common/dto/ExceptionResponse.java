package com.sparta.wuzuzu.domain.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionResponse {

    String msg;
    int httpCode;
}
