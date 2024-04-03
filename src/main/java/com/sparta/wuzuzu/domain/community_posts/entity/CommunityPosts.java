package com.sparta.wuzuzu.domain.community_posts.entity;

import com.sparta.wuzuzu.domain.common.entity.Timestamped;
import com.sparta.wuzuzu.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "community_posts")
public class CommunityPosts extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long views = 0L;

    @Column(nullable = false)
    private Long likeCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @OneToMany(mappedBy = "communityPosts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post_likes> postlikesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Community_Category category;

    public CommunityPosts(String title, Community_Category category, String content, User user) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.user = user;

    }

    public int getLikesCount() {
        return postlikesList.size();
    }

    public void addLike() {
        this.likeCount++;
    }

    public void removeLike() {
        this.likeCount = Math.max(0, this.likeCount - 1); // 0 미만으로 내려가지 않도록 보장
    }

    public void increaseViews(){
        views++;
    }

    public boolean validateUser(Long loginUserId) {
        return this.user.getUserId().equals(loginUserId);
    }

    public void updateCommunityPosts(String title, Community_Category category, String content) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
