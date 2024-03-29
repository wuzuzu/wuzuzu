package com.sparta.wuzuzu.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequest {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotBlank(message = "재고 아이디는 필수입니다.")
    private Long stuffId;

    @NotBlank(message = "판매 수량은 필수입니다.")
    private Long count;
}
