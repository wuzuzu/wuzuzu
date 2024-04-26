package com.sparta.wuzuzu.domain.community_posts.dto;

import com.sparta.wuzuzu.global.dto.ListRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CommunityPostListRequest extends ListRequest {

    private String keyword;
    private String categoryName;
}
