package com.sparta.wuzuzu.domain.dibs.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.dibs.dto.DibsProjection;
import com.sparta.wuzuzu.domain.dibs.service.DibsService;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dibs")
public class DibsController {
    private final DibsService dibsService;

    User testUser = User.builder()
        .userId(1L)
        .email("test@test.com")
        .password("password")
        .userName("userName")
        .build();

    @PostMapping("/post/{postId}")
    public ResponseEntity<Void> createDibs(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long postId
    ){
        dibsService.createDibs(testUser, postId);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<DibsProjection>>> getOrders(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        List<DibsProjection> dibsResponseList = dibsService.getDibs(testUser);
        return CommonResponse.ofDataWithHttpStatus(dibsResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteDibs(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long postId
    ){
        dibsService.deleteDibs(testUser, postId);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }
}

