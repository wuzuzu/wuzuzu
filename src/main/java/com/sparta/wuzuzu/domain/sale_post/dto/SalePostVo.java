package com.sparta.wuzuzu.domain.sale_post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalePostVo {
    private Long salePostId;
    private String title;
    private String description;
    private Long views;
    private String author;
    private String category;
    private Boolean status;
    private String goods;
    private Long price;
    private Long stock;
}
