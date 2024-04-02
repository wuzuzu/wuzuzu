package com.sparta.wuzuzu.domain.community_posts.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityPostsResponse {

    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdDate;

    public CommunityPostsResponse(String title, String username, String contents, LocalDateTime createdDate) {}


}
