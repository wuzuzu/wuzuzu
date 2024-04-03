package com.sparta.wuzuzu.domain.comment.dto;

import com.sparta.wuzuzu.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {

    private Long commentId;
    private String username;
    private String contents;


    public CommentResponse(Comment comment){
        this.commentId = comment.getCommentId();
        this.username = comment.getUser().getUserName();
        this.contents = comment.getContents();
    }
}
