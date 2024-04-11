package com.sparta.wuzuzu.global.util;

import com.sparta.wuzuzu.domain.community_posts.entity.CommunityCategory;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPost;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityCategoryRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

//@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CommunityPostRepository communityPostRepository;

    private final UserRepository userRepository;

    private final CommunityCategoryRepository communityCategoryRepository;

    @Override
    public void run(String... args) throws Exception {
        List<CommunityPost> posts = new ArrayList<>();

        User testUser = userRepository.findById(1L).orElse(null);
        CommunityCategory testCategory = communityCategoryRepository.findById(1L).orElse(null);

        for (int i = 0; i < 10000; i++) {
            CommunityPost post = CommunityPost.builder()
                .title("Title " + i)
                .content("Content for post " + i)
                .views((long) (Math.random() * 1000))
                .likeCount((long) (Math.random() * 100))
                .user(testUser) // 임시 사용자 객체 할당
                .category(testCategory) // 임시 카테고리 객체 할당
                .build();
            posts.add(post);
        }

        for (int i = 0; i < 10000; i++) {
            CommunityPost post = CommunityPost.builder()
                .title("dog " + i)
                .content("Content for post " + i)
                .views((long) (Math.random() * 1000))
                .likeCount((long) (Math.random() * 100))
                .user(testUser) // 임시 사용자 객체 할당
                .category(testCategory) // 임시 카테고리 객체 할당
                .build();
            posts.add(post);
        }

        communityPostRepository.saveAll(posts);
    }

}
