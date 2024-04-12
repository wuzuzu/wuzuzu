package com.sparta.wuzuzu.domain.comment.controller;


import com.sparta.wuzuzu.domain.comment.dto.CommentRequest;
import com.sparta.wuzuzu.domain.comment.dto.CommentResponse;
import com.sparta.wuzuzu.domain.comment.service.CommentSerivce;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentSerivce commentSerivce;


    @PostMapping("/communityposts/{postid}")
    public ResponseEntity<CommentResponse> postComment(@PathVariable("postid") Long postid,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails, @RequestBody CommentRequest commentRequest) {

        CommentResponse commentResponse = commentSerivce.createComment(postid, userDetails.getUser(),
            commentRequest);
        return ResponseEntity.ok().body(commentResponse);
    }

    @GetMapping("/communitypost/{postid}")
    public ResponseEntity<List<CommentResponse>> getCommentByCommunityPost(@PathVariable Long postid) {
        return ResponseEntity.ok().body(commentSerivce.getCommentByCommunityPost(postid));

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentSerivce.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok().build();

    }

}
