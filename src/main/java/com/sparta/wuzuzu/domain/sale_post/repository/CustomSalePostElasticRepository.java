package com.sparta.wuzuzu.domain.sale_post.repository;

import com.sparta.wuzuzu.domain.sale_post.dto.SalePostElasticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomSalePostElasticRepository {
    Page<SalePostElasticResponse> findByTitleAndGoodsContaining(String keyword, Pageable pageable);
}
