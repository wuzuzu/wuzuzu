package com.sparta.wuzuzu.domain.community_posts.service;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostGet;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityCategory;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostsRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityCategoryRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.global.exception.ValidateUserException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityPostsService {

    private final CommunityPostsRepository communityPostsRepository;
    private final CommunityCategoryRepository community_CategoryRepository;

    public CommunityPostsResponse saveCommunityPosts(CommunityPostsRequest communityPostsRequest,
        User user) {

        CommunityCategory community_Category = community_CategoryRepository.findByNameEquals(
                communityPostsRequest.getCategoryName())
            .orElseThrow();
        if (community_Category.getStatus() == false) {
            throw new NoSuchElementException();
        }

        communityPostsRepository.save(new CommunityPosts(
                communityPostsRequest.getTitle(),
                community_Category,
                communityPostsRequest.getContent(),
                user
            )
        );
        return CommunityPostsResponse.builder().
            title(communityPostsRequest.getTitle()).
            username(user.getUserName()).
            contents(communityPostsRequest.getContent()).
            build();

    }

    @Transactional
    public CommunityPostsResponse updateCommunityPosts(CommunityPostsRequest communityPostsRequest,
        Long userId, Long communityPostsId) {
        CommunityPosts post = communityPostsRepository.findById(communityPostsId).orElseThrow();
        post.validateUser(userId);
        post.updateCommunityPosts(
            communityPostsRequest.getTitle(),
            community_CategoryRepository.findByNameEquals(communityPostsRequest.getCategoryName())
                .orElseThrow(),
            communityPostsRequest.getContent());
        return CommunityPostsResponse.builder().
            title(post.getTitle()).
            username(post.getUser().getUserName()).
            contents(post.getContent()).
            build();

    }

    public Page<CommunityPostGet> getCommunityPosts(int page, int size, String sortBy,
        boolean isAsc) {
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        // CommunityPosts의 Page를 CommunityPostGet의 Page로 변환
        return communityPostsRepository.findAll(pageable)
            .map(communityPosts -> new CommunityPostGet(communityPosts.getTitle(),
                communityPosts.getViews(), communityPosts.getLikesCount()));
    }

    public Page<CommunityPostsResponse> getPostsByKeyword(String keyword, int page, int size,
        String sortBy,
        boolean isAsc) {

        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommunityPosts> posts = communityPostsRepository.findByTitleContaining(keyword,
            pageable);

        if (posts.isEmpty()) {
            throw new NoSuchElementException("다음 키워드를 포함한 제목이 없습니다. " + keyword);
        }

        return posts.map(communityPosts -> CommunityPostsResponse.builder().
            title(communityPosts.getTitle()).
            username(communityPosts.getUser().getUserName()).
            contents(communityPosts.getContent()).
            views(communityPosts.getViews()).
            build());

    }

    public Page<CommunityPostsResponse> getPostsByCategory(String categoryName, int page, int size,
        String sortBy, boolean isAsc) {
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CommunityPosts> posts = communityPostsRepository.findByCategoryName(categoryName,
            pageable);
        if (posts.isEmpty()) {
            throw new NoSuchElementException("해당 카테고리의 글이 없습니다.");
        }

        return posts.map(communityPosts -> CommunityPostsResponse.builder().
            title(communityPosts.getTitle()).
            username(communityPosts.getUser().getUserName()).
            contents(communityPosts.getContent()).
            views(communityPosts.getViews()).
            build());

    }
    @Transactional
    public CommunityPostsResponse getDetail(Long communitypostsId) {
        CommunityPosts post = communityPostsRepository.findById(communitypostsId)
            .orElseThrow(() -> new NoSuchElementException("해당 글을 찾을 수 없습니다."));
        post.increaseViews();
        return CommunityPostsResponse.builder().
            title(post.getTitle()).
            username(post.getUser().getUserName()).
            contents(post.getContent()).
            views(post.getViews()).
            build();
    }

    @Transactional
    public void deleteCommunityPost(Long communityPostsID, User user) {
        communityPostsRepository.findById(communityPostsID)
            .filter(communityPosts -> user.getUserId().equals(communityPosts.getUser().getUserId()))
            .orElseThrow(ValidateUserException::new); // 사용자 ID가 게시글 작성자의 ID와 일치하지 않을 경우 예외 발생

        communityPostsRepository.deleteById(communityPostsID); // 게시글 삭제
    }


}
