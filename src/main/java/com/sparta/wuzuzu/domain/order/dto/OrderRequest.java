package com.sparta.wuzuzu.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotNull(message = "게시글 아이디는 필수입니다.")
    private Long postId;

    @Positive(message = "주문 수량은 양수이어야 합니다.")
    private Long count;
}
