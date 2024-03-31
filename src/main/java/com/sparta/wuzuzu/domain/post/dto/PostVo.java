package com.sparta.wuzuzu.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostVo {
    private Long postId;
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
