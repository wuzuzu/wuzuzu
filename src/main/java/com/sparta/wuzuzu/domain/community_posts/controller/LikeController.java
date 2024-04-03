package com.sparta.wuzuzu.domain.community_posts.controller;


import com.sparta.wuzuzu.domain.community_posts.dto.PostLikeResponse;
import com.sparta.wuzuzu.domain.community_posts.service.Post_likeService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communityposts")
public class LikeController {

    private final Post_likeService likeService;

    @PostMapping("/{communitypostId}/likes")
    public ResponseEntity<PostLikeResponse> createLike(@PathVariable Long communitypostId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
            .body(likeService.createLike(communitypostId, userDetails.getUser()));
    }

}
