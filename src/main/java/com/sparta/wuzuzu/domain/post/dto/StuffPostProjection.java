package com.sparta.wuzuzu.domain.post.dto;

import com.sparta.wuzuzu.domain.stuff.entity.StuffType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StuffPostProjection {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Boolean postStatus;
    private Long postViews;
    private String stuffName;
    private String stuffDescription;
    private Long stuffPrice;
    private Long stuffStock;
    private StuffType stuffCategory;
}
