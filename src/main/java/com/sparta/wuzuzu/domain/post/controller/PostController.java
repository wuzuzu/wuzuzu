package com.sparta.wuzuzu.domain.post.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.post.dto.PostProjection;
import com.sparta.wuzuzu.domain.post.dto.PostRequest;
import com.sparta.wuzuzu.domain.post.dto.PostResponse;
import com.sparta.wuzuzu.domain.post.service.PostService;
import com.sparta.wuzuzu.domain.user.entity.User;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;


    User testUser = User.builder()
        .userId(1L)
        .email("test@test.com")
        .password("password")
        .userName("userName")
        .build();

    @PostMapping
    public ResponseEntity<Void> createPost(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @Valid @RequestBody PostRequest requestDto
    ){
        postService.createPost(testUser, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    // 전체 게시글 목록 조회 : createAt 기준으로 pageable 사용하기
    @GetMapping
    public ResponseEntity<CommonResponse<List<PostResponse>>> getPosts()
    {
        List<PostResponse> postResponseList = postService.getPosts();
        return CommonResponse.ofDataWithHttpStatus(postResponseList, HttpStatus.OK);
    }

    // 게시물 상세 조회 : QueryDSL 사용하기, 조회시 동시성 제어로 조회수 증가
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostProjection>> getPost(
        @PathVariable Long postId
    ){
        PostProjection response = postService.getPost(postId);
        return CommonResponse.ofDataWithHttpStatus(response, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @PathVariable Long postId,
        @Valid @RequestBody PostRequest requestDto
    ){
        postService.updatePost(testUser, postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @PostMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @PathVariable Long postId
    ){
        postService.deletePost(testUser, postId);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }
}
