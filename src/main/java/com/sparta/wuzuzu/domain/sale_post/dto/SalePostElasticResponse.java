package com.sparta.wuzuzu.domain.sale_post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.wuzuzu.domain.category.entity.Category;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.experimental.categories.Categories;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 필드 무시
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalePostElasticResponse {

    private Long sale_post_id;
    private String title;
    private Long views;
    private String goods;
    private Long stock;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date timestamp;

    private String author;
    private String category;

}