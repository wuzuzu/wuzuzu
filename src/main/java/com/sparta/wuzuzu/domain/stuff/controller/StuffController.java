package com.sparta.wuzuzu.domain.stuff.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.stuff.dto.StuffRequest;
import com.sparta.wuzuzu.domain.stuff.dto.StuffResponse;
import com.sparta.wuzuzu.domain.stuff.service.StuffService;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stuffs")
public class StuffController {
    private final StuffService stuffService;

    User testUser = User.builder()
        .userId(1L)
        .email("test@test.com")
        .password("password")
        .userName("userName")
        .build();

    @PostMapping
    public ResponseEntity<Void> createStuff(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody StuffRequest requestDto
    ){
        stuffService.createStuff(testUser, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();   // 응답의 본문 없음
    }

    // 관련된 게시글 목록도 가져오면 좋을듯..?
    @GetMapping
    public ResponseEntity<CommonResponse<List<StuffResponse>>> getStuffs(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
    ){
        List<StuffResponse> stuffResponseList = stuffService.getStuffs(testUser);
        return CommonResponse.ofDataWithHttpStatus(stuffResponseList, HttpStatus.OK);
    }

    @PutMapping("/{stuffId}")
    public ResponseEntity<Void> updateStuff(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @PathVariable Long stuffId,
        @RequestBody StuffRequest requestDto
    ){
        stuffService.updateStuff(testUser, stuffId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
