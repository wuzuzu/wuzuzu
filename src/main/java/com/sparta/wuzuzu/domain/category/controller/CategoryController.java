package com.sparta.wuzuzu.domain.category.controller;

import com.sparta.wuzuzu.domain.category.dto.CategoryRequest;
import com.sparta.wuzuzu.domain.category.service.CategoryService;
import com.sparta.wuzuzu.domain.post.dto.PostRequest;
import com.sparta.wuzuzu.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
public class CategoryController {
    private final CategoryService categoryService;

    User testUser = User.builder()
        .userId(1L)
        .email("test@test.com")
        .password("password")
        .userName("userName")
        .build();

    @PostMapping
    public ResponseEntity<Void> createPost(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @Valid @RequestBody CategoryRequest requestDto
    ){
        categoryService.createCategory(testUser, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePost(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @Valid @RequestBody CategoryRequest requestDto
    ){
        categoryService.updatePost(testUser, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<Void> deletePost(
        //@AuthenticationPrincipal UserDetailsImpl userDetails
        @PathVariable Long categoryId
    ){
        categoryService.deletePost(testUser, categoryId);
        return ResponseEntity.status(HttpStatus.OK.value()).build();
    }
}
