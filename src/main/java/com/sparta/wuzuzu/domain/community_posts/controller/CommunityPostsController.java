package com.sparta.wuzuzu.domain.community_posts.controller;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsListRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsListResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.service.CommunityPostsService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/communityposts")
@RestController
@RequiredArgsConstructor
public class CommunityPostsController {

    private final CommunityPostsService communityPostsService;

    @PostMapping
    public ResponseEntity<CommunityPostsResponse> saveCommunityPosts(
        @RequestBody @Valid CommunityPostsRequest communityPostsRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommunityPostsResponse communityPostsResponse = communityPostsService.saveCommunityPosts(
            communityPostsRequest,
            userDetails.getUser());

        return new ResponseEntity<>(communityPostsResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{communityPostsId}")
    public ResponseEntity<CommunityPostsResponse> updateCommunityPost(
        @Valid @RequestBody CommunityPostsRequest communityPostsRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long communityPostsId
    ) {
        CommunityPostsResponse communityPostsResponse = communityPostsService.updateCommunityPosts(
            communityPostsRequest, userDetails.getUser().getUserId(), communityPostsId);

        return new ResponseEntity<>(communityPostsResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<CommunityPostsListResponse> searchCommunityPosts(@ModelAttribute CommunityPostsListRequest request) {
        return ResponseEntity.ok(communityPostsService.searchCommunityPosts(request));
    }

    @GetMapping("/read/{communityPostsId}")
    public ResponseEntity<CommunityPostsResponse> readDetail(@PathVariable Long communityPostsId) {
        return ResponseEntity.ok().body(communityPostsService.getDetail(communityPostsId));
    }

    @DeleteMapping("/{communityPostsId}")
    public ResponseEntity<Void> deleteCommunityPosts(
        @PathVariable Long communityPostsId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        communityPostsService.deleteCommunityPost(communityPostsId, userDetails.getUser());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
