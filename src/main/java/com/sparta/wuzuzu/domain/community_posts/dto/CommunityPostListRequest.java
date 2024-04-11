package com.sparta.wuzuzu.domain.community_posts.dto;

import com.sparta.wuzuzu.global.dto.request.ListRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CommunityPostListRequest extends ListRequest {


    private String keyword;
    private String categoryName;

    public CommunityPostListRequest() {
        this.keyword = "";
        this.categoryName = "";
    }

}
