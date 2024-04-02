package com.sparta.wuzuzu.domain.community_posts.service;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityCategoryRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityCategoryResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.Community_Category;
import com.sparta.wuzuzu.domain.community_posts.repository.Community_CategoryRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.global.exception.ValidateAdminException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Community_CategoryService {

    private final Community_CategoryRepository community_CategoryRepository;

    public CommunityCategoryResponse createCategory(User user,
        CommunityCategoryRequest communityCategoryRequest) {

        UserRole userRole = user.getRole();
        if (userRole == UserRole.ADMIN) {
            Optional<Community_Category> category = community_CategoryRepository.findByNameEquals(
                communityCategoryRequest.getName());
            if (category.isPresent()) {
                if (category.get().getStatus()) {
                    throw new IllegalArgumentException("중복된 카테고리 이름입니다.");
                } else {
                    category.get().reCreate();
                }
            } else {
                community_CategoryRepository.save(
                    new Community_Category(communityCategoryRequest.getName()));
            }

            return new CommunityCategoryResponse(category.get());
        } else {
            throw new ValidateAdminException();
        }

    }

    public List<CommunityCategoryResponse> getCategory() {
        List<Community_Category> community_Categories = community_CategoryRepository.findAll();
        if (community_Categories.size() == 0) {
            throw new IllegalArgumentException("저장된 카테고리가 없습니다.");
        }
        return community_Categories.stream().
            map(CommunityCategoryResponse::new).
            collect(Collectors.toList());

    }

    public void updateCategory(User user, Long categoryId, CommunityCategoryRequest request) {
        if (user.getRole() == UserRole.ADMIN) {
            Community_Category category = community_CategoryRepository.findById(categoryId)
                .orElseThrow(
                    NoSuchElementException::new);
            category.update(request.getName());

        }
        ;
    }

    public void deleteCategory(User user, Long categoryId) {
        if (user.getRole() == UserRole.ADMIN) {
            Community_Category community_Category = community_CategoryRepository.findById(
                categoryId).orElseThrow(NoSuchElementException::new);
            if (!community_Category.getStatus()) {
                throw new IllegalArgumentException("이미 삭제 된 카테고리입니다.");
            } else {
                community_Category.delete();
            }
        } else {
            throw new ValidateAdminException();
        }
    }
}
