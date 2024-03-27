package com.sparta.wuzuzu.domain.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CommonResponse<T> {

    T data;
}
