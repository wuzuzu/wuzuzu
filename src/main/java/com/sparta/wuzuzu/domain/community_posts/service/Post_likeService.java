package com.sparta.wuzuzu.domain.community_posts.service;

import com.sparta.wuzuzu.domain.community_posts.dto.PostLikeResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import com.sparta.wuzuzu.domain.community_posts.entity.Post_likes;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostsRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.Post_likeRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Post_likeService {

    private final CommunityPostsRepository communityPostsRepository;
    private final Post_likeRepository post_likeRepository;

    @Transactional(readOnly = true)
    public PostLikeResponse createLike(Long communitypostId, User user) {
        CommunityPosts post = communityPostsRepository.findById(communitypostId)
            .orElseThrow(() -> new NotFoundPostException());

        // 이미 좋아요가 존재하면 삭제
        if (post_likeRepository.findByUserAndCommunityPosts(user, post).isPresent()) {
            Post_likes like = post_likeRepository.findByUserAndCommunityPosts(user, post).get();
            like.removeLike(post);//게시글 좋아요 리스트에서 제거.
            communityPostsRepository.save(post);
            return new PostLikeResponse("좋아요가 취소되었습니다");
        } else {
            Post_likes like = new Post_likes(user, post);
            like.addLike(post); // 게시글에 좋아요 추가(리스트 형태)
            communityPostsRepository.save(post);

            return new PostLikeResponse(like, "좋아요 하셨습니다");

        }
    }

}
