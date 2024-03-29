package com.sparta.wuzuzu.domain.category.service;

import com.sparta.wuzuzu.domain.category.dto.CategoryRequest;
import com.sparta.wuzuzu.domain.category.repository.CategoryRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public final CategoryRepository categoryRepository;

    public void createCategory(User testUser, CategoryRequest requestDto) {
    }

    public void updatePost(User testUser, CategoryRequest requestDto) {
    }

    public void deletePost(User testUser, Long categoryId) {
    }
}
