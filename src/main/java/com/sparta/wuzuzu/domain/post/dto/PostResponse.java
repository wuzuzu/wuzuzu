package com.sparta.wuzuzu.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sparta.wuzuzu.domain.post.entity.Post;
import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import com.sparta.wuzuzu.domain.stuff.entity.StuffType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private Long views;
    private String stuffName;
    private Long stuffPrice;
    private StuffType stuffType;

    public PostResponse(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.views = post.getViews();
    }
}
