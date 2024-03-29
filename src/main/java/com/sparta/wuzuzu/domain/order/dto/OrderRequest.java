package com.sparta.wuzuzu.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotBlank(message = "게시글 아이디는 필수입니다.")
    private Long postId;

    @NotBlank(message = "주문 수량은 필수입니다.")
    private Long count;
}
