package com.sparta.wuzuzu.domain.community_posts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.wuzuzu.domain.community_posts.entity.Community_Category;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityCategoryResponse {

    private String name;
    private boolean status;

    public CommunityCategoryResponse(Community_Category category) {

        this.name = category.getName();
        this.status = category.getStatus();
    }

}
