package com.sparta.wuzuzu.domain.community_posts.entity;

import com.sparta.wuzuzu.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likePosts")
public class Postlikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_postsid", nullable = false)
    private CommunityPosts communityPosts;

    public Postlikes(User user, CommunityPosts communityPosts) {
        this.user = user;
        this.communityPosts = communityPosts;
    }

    public void addLike(CommunityPosts communityPosts) {
        this.communityPosts = communityPosts;
        communityPosts.getPostlikesList().add(this);
    }

    public void removeLike(CommunityPosts communityPosts) {
        this.communityPosts = communityPosts;
        communityPosts.getPostlikesList().remove(this);
    }
}

