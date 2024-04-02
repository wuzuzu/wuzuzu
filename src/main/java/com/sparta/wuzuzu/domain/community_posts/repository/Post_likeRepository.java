package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import com.sparta.wuzuzu.domain.community_posts.entity.Post_likes;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Post_likeRepository extends JpaRepository<Post_likes , Long> {

    Optional<Post_likes> findByUserAndCommunityPosts(User user, CommunityPosts communityPosts);

}
