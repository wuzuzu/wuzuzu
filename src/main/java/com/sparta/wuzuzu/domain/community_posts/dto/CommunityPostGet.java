package com.sparta.wuzuzu.domain.community_posts.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityPostGet {

    private String title;
    private Long views;
    private int likeCount;

    // 필요한 생성자를 추가합니다.
    public CommunityPostGet(String title, Long views, int likeCount) {
        this.title = title;
        this.views = views;
        this.likeCount = likeCount;
    }

}
