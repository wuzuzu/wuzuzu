package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import com.sparta.wuzuzu.domain.community_posts.entity.Postlikes;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostlikeRepository extends JpaRepository<Postlikes, Long> {

    Optional<Postlikes> findByUserAndCommunityPosts(User user, CommunityPosts communityPosts);

}
