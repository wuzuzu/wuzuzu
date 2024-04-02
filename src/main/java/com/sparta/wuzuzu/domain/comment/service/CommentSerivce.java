package com.sparta.wuzuzu.domain.comment.service;


import com.sparta.wuzuzu.domain.comment.dto.CommentRequest;
import com.sparta.wuzuzu.domain.comment.dto.CommentResponse;
import com.sparta.wuzuzu.domain.comment.entity.Comment;
import com.sparta.wuzuzu.domain.comment.repository.CommentRepository;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostsRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.global.exception.ValidateUserException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentSerivce {

    CommunityPostsRepository communityPostsRepository;
    CommentRepository commentRepository;

    public CommentResponse createComment(Long postid, User user, CommentRequest commentRequest) {
        CommunityPosts communityPosts = communityPostsRepository.findById(postid).orElseThrow(
            NoSuchElementException::new);
        Comment comment = new Comment(communityPosts, user, commentRequest.getContents());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    public List<CommentResponse> getCommentByCommunityPost(Long postid) {

        List<Comment> commentsList = commentRepository.findAllByPostIdOrderByCreatedAtCommentIdDesc(
            postid);
        if (commentsList.isEmpty()) {
            throw new NoSuchElementException();
        }
        return commentsList.stream().map(s -> new CommentResponse(s)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);
        if (comment.getUser().equals(user)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ValidateUserException();
        }
    }
}
