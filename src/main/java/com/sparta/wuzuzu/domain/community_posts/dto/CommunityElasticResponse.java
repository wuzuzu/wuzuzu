package com.sparta.wuzuzu.domain.community_posts.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Long communitypost_id;
    private String title;
    private String content;
    private Long views;
    private Long like_count;
    private Integer comments;
    private Date timestamp;

    private Long user_id;
    private String user_name;

    private String category_name;
}