package com.sparta.wuzuzu.domain.community_posts.dto;

import com.sparta.wuzuzu.domain.community_posts.entity.Post_likes;
import com.sparta.wuzuzu.domain.user.entity.User;

public class PostLikeResponse {
        private Long postlikeId;
        private User user;
        private String msg;

        public PostLikeResponse(Post_likes like, String msg){
            this.postlikeId = like.getLikeId();
            this.user = like.getUser();
            this.msg = msg;

        }

        public PostLikeResponse (String msg){
            this.msg = msg;
        }

    }


