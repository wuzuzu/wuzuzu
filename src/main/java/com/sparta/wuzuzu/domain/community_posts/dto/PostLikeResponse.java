package com.sparta.wuzuzu.domain.community_posts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.wuzuzu.domain.community_posts.entity.Post_likes;
import com.sparta.wuzuzu.domain.user.entity.User;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostLikeResponse {
        private Long postlikeId;
        private String msg;

        public PostLikeResponse(Post_likes like, String msg){
            this.postlikeId = like.getLikeId();
            this.msg = msg;

        }

        public PostLikeResponse (String msg){
            this.msg = msg;
        }

    }


