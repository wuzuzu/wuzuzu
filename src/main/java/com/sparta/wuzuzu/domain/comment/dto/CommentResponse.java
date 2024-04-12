package com.sparta.wuzuzu.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.wuzuzu.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
