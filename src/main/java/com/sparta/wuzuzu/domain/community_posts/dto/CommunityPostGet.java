package com.sparta.wuzuzu.domain.community_posts.dto;

import lombok.Builder;
import lombok.Getter;

public class CommunityPostGet {

    @Getter
    @Builder
    public class CommunityPostsResponse {

        private String title;
        private int likeCount;

        // 필요한 생성자를 추가합니다.
        public CommunityPostsResponse(String title, int likeCount) {
            this.title = title;
            this.likeCount = likeCount;
        }

    }
}