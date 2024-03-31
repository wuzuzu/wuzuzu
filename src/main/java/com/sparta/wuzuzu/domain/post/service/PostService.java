package com.sparta.wuzuzu.domain.post.service;

import com.sparta.wuzuzu.domain.category.entity.Category;
import com.sparta.wuzuzu.domain.category.repository.CategoryRepository;
import com.sparta.wuzuzu.domain.post.dto.PostVo;
import com.sparta.wuzuzu.domain.post.dto.PostRequest;
import com.sparta.wuzuzu.domain.post.dto.PostResponse;
import com.sparta.wuzuzu.domain.post.entity.Post;
import com.sparta.wuzuzu.domain.post.repository.PostRepository;
import com.sparta.wuzuzu.domain.post.repository.query.PostQueryRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createPost(
        User user,
        PostRequest requestDto
    ) {
        Category category = categoryRepository.findByName(requestDto.getCategory()).orElseThrow(
            () -> new IllegalArgumentException("카테고리가 존재하지 않습니다.")
        );

        postRepository.save(new Post(user, requestDto, category));
    }

    // 전체 게시글 : 제목, 조회수만 출력
    // 전체 게시글 목록 조회 : createAt 기준으로 pageable 사용하기(미적용)
    // Category 와 User 의 name 을 가져오면서 N+1 문제 생김 -> QueryDSL 로 변경하기
    @Transactional
    public List<PostResponse> getPosts()
    {
        List<Post> postList = postRepository.findAllByStatusTrue();

        if(postList.isEmpty()){
            throw new IllegalArgumentException("postList is empty.");
        }

        return postList.stream()
            .map(PostResponse::new)
            .collect(Collectors.toList());
    }

    // 상세 게시글 : 제목, 조회수, 설명, 상품 내용 출력
    // 게시물 상세 조회 : QueryDSL 사용하기
    @Transactional
    public PostVo getPost(
        Long postId
    ) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException("post is empty.")
        );

        if(!post.getStatus()){
            throw new IllegalArgumentException("post is deleted");
        }

        post.increaseViews();   // 동시성 제어 고려

        return postQueryRepository.findPostByPostId(postId);
    }

    @Transactional
    public void updatePost(
        User user,
        Long postId,
        PostRequest requestDto
    ) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException("post is empty.")
        );

        if(!user.getUserId().equals(post.getUser().getUserId())){
            throw new IllegalArgumentException("post is not yours");
        }

        if(!post.getStatus()){
            throw new IllegalArgumentException("post is deleted.");
        }

        Category category = categoryRepository.findByName(requestDto.getCategory()).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 카테고리 입니다.")
        );

        post.update(requestDto, category);
    }

    @Transactional
    public void deletePost(
        User user,
        Long postId
    ) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException("post is empty.")
        );

        if(!user.getUserId().equals(post.getUser().getUserId())){
            throw new IllegalArgumentException("post is not yours");
        }

        if(!post.getStatus()){
            throw new IllegalArgumentException("post is already deleted.");
        }

        post.delete();
    }
}
