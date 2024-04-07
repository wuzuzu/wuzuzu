package com.sparta.wuzuzu.domain.community_posts.service;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsListRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsListResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityCategory;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPost;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostsRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityCategoryRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CustomCommunityPostsRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.global.exception.ValidateUserException;
import com.sparta.wuzuzu.global.util.PagingUtil;
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
    private final CustomCommunityPostsRepository customCommunityPostsRepository;

    public CommunityPostsResponse saveCommunityPosts(CommunityPostsRequest communityPostsRequest,
        User user) {

        CommunityCategory community_Category = community_CategoryRepository.findByNameEquals(
                communityPostsRequest.getCategoryName())
            .orElseThrow();
        if (community_Category.getStatus() == false) {
            throw new NoSuchElementException();
        }

        communityPostsRepository.save(new CommunityPost(
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
        CommunityPost post = communityPostsRepository.findById(communityPostsId).orElseThrow();
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


    public CommunityPostsListResponse searchCommunityPosts(CommunityPostsListRequest request) {
        if (request.getColumn() == null) {
            request.setColumn("createdDate");
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(),
            Sort.by(request.getSortDirection(), request.getColumn()));
        Page<CommunityPostsResponse> postsPage = customCommunityPostsRepository.findByConditions(
            request.getKeyword(), request.getCategoryName(), pageable);

        PagingUtil pagingUtil = new PagingUtil(postsPage.getTotalElements(),
            postsPage.getTotalPages(), request.getPage(), request.getPageSize());

        return CommunityPostsListResponse.builder()
            .postList(postsPage.getContent())
            .pagingUtil(pagingUtil)
            .build();
    }

    @Transactional
    public CommunityPostsResponse getDetail(Long communitypostsId) {
        CommunityPost post = communityPostsRepository.findById(communitypostsId)
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
