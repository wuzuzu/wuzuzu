package com.sparta.wuzuzu.domain.post.entity;

import com.sparta.wuzuzu.domain.common.entity.Timestamped;
import com.sparta.wuzuzu.domain.post.dto.PostRequest;
import com.sparta.wuzuzu.domain.user.entity.User;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(nullable = false)
    private Long stuffId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean status = true;

    @Column(nullable = false)
    private Long views = 0L;

    public Post(PostRequest requestDto, User user) {
        this.user = user;
        this.stuffId = requestDto.getStuffId();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void update(PostRequest requestDto) {
        this.stuffId = requestDto.getStuffId();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void increaseViews(){
        views++;
    }

    public void delete() {
        status = false;
    }
}