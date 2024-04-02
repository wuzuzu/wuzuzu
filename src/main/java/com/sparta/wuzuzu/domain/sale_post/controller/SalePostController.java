package com.sparta.wuzuzu.domain.sale_post.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.sale_post.dto.SalePostVo;
import com.sparta.wuzuzu.domain.sale_post.dto.SalePostRequest;
import com.sparta.wuzuzu.domain.sale_post.dto.SalePostResponse;
import com.sparta.wuzuzu.domain.sale_post.service.SalePostService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salePosts")
public class SalePostController {
    private final SalePostService salePostService;

    @PostMapping
    public ResponseEntity<Void> createSalePost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody SalePostRequest requestDto
    ){
        salePostService.createSalePost(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    // 전체 게시글 목록 조회 : createAt 기준으로 pageable 사용하기
    @GetMapping
    public ResponseEntity<CommonResponse<List<SalePostResponse>>> getSalePosts()
    {
        List<SalePostResponse> salePostResponseList = salePostService.getSalePosts();
        return CommonResponse.ofDataWithHttpStatus(salePostResponseList, HttpStatus.OK);
    }

    // 게시물 상세 조회 : QueryDSL 사용하기, 조회시 동시성 제어로 조회수 증가
    @GetMapping("/{salePostId}")
    public ResponseEntity<CommonResponse<SalePostVo>> getPost(
        @PathVariable Long salePostId
    ){
        SalePostVo response = salePostService.getSalePost(salePostId);
        return CommonResponse.ofDataWithHttpStatus(response, HttpStatus.OK);
    }

    @PutMapping("/{salePostId}")
    public ResponseEntity<Void> updatePost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long salePostId,
        @Valid @RequestBody SalePostRequest requestDto
    ){
        salePostService.updateSalePost(userDetails.getUser(), salePostId, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @PostMapping("/{salePostId}/delete")
    public ResponseEntity<Void> deletePost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long salePostId
    ){
        salePostService.deleteSalePost(userDetails.getUser(), salePostId);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }
}
