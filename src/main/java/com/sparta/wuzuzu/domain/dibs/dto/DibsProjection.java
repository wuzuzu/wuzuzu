package com.sparta.wuzuzu.domain.dibs.dto;

import com.sparta.wuzuzu.domain.stuff.entity.StuffType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DibsProjection {
    private Long postId;
    private String postTitle;
    private Boolean postStatus;
    private Long postViews;
    private Long stuffId;
    private String stuffName;
    private Long stuffPrice;
    private StuffType stuffCategory;
}