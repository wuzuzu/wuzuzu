package com.sparta.wuzuzu.domain.sale_post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.wuzuzu.global.dto.ListResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalePostElasticListResponse extends ListResponse {

   private List<SalePostElasticResponse> responseList;
}
