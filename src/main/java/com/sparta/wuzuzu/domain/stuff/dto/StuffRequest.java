package com.sparta.wuzuzu.domain.stuff.dto;

import com.sparta.wuzuzu.domain.stuff.entity.StuffType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StuffRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotBlank(message = "가격은 필수입니다.")
    private Long price;

    @NotBlank(message = "재고는 필수입니다.")
    private Long stock;

    @NotBlank(message = "카테고리는 필수입니다.")
    private StuffType category;
}