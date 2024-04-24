package com.sparta.wuzuzu.domain.sale_post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Long salePostId;
    private String title;
    private String description;
    private Long views = 0L;
    private Boolean status = true;
    private String goods;
    private Long price;
    private Long stock;
    private Category category;
    private User user;
    private Date timestamp;

    @Data
    public static class User {
        private long userId;
    }

    @Data
    public static class Category {
        private long categoryId;
        private String name;
    }

}
