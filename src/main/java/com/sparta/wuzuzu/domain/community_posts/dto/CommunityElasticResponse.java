package com.sparta.wuzuzu.domain.community_posts.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityCategory;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 필드 무시
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityElasticResponse {
    private String title;
    private String content;
    private Long views;
    private Long likeCount;
    private Integer comments;
    private Date timestamp;  // @timestamp 필드 추가

    private User user;
    private CommunityCategory category;

    @Data
    public static class User {
        private long userId;
    }

    @Data
    public static class CommunityCategory {
        private long categoryId;
        private String name;
    }

}
