package com.sparta.wuzuzu.domain.community_posts.controller;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostGet;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.service.CommunityPostsService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PatchMapping("/{communityposts_id}")
    public ResponseEntity<CommunityPostsResponse> updateCommunityPost(
        @Valid @RequestBody CommunityPostsRequest communityPostsRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long communityposts_id
    ) {
        CommunityPostsResponse communityPostsResponse = communityPostsService.updateCommunityPosts(
            communityPostsRequest, userDetails.getUser().getUserId(), communityposts_id);

        return new ResponseEntity<>(communityPostsResponse, HttpStatus.OK);
    }

    @GetMapping("/read")
    public Page<CommunityPostGet> getCommunityPosts(
        @RequestParam("page") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc) {
        return communityPostsService.getCommunityPosts(
            page - 1, size, sortBy, isAsc);
    }

    @GetMapping("/read/title")
    public Page<CommunityPostsResponse> getPostsByKeyword(
        @RequestParam("keyword") String keyword,
        @RequestParam("page") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc) {

        return communityPostsService.getPostsByKeyword(keyword, page - 1, size, sortBy, isAsc);
    }

    @GetMapping("/posts/category/{categoryName}")
    public Page<CommunityPostsResponse> getPostsByCategory(
        @PathVariable String categoryName,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc) {

        return communityPostsService.getPostsByCategory(categoryName, page-1, size, sortBy, isAsc);
    }

    @GetMapping("/read/{communityposts_id}")
    public ResponseEntity<CommunityPostsResponse> readDetail(@PathVariable Long communityposts_id) {
        return ResponseEntity.ok().body(communityPostsService.getDetail(communityposts_id));
    }

    @DeleteMapping("/{communityposts_id}")
    public ResponseEntity<Void> deleteCommunityPosts(
        @PathVariable Long communityposts_id,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        communityPostsService.deleteCommunityPost(communityposts_id, userDetails.getUser());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
