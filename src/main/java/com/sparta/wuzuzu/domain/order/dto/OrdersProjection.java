package com.sparta.wuzuzu.domain.order.dto;

import com.sparta.wuzuzu.domain.stuff.entity.StuffType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersProjection {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Boolean postStatus;
    private Long postViews;
    private Long stuffId;
    private String stuffName;
    private String stuffDescription;
    private Long stuffPrice;
    private StuffType stuffCategory;
}
