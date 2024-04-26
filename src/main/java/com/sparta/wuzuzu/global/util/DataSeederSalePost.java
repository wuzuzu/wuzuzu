package com.sparta.wuzuzu.global.util;

import com.sparta.wuzuzu.domain.category.entity.Category;
import com.sparta.wuzuzu.domain.category.repository.CategoryRepository;
import com.sparta.wuzuzu.domain.sale_post.entity.SalePost;
import com.sparta.wuzuzu.domain.sale_post.repository.SalePostRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

//@Component //사용할 때만 주석처리 제거
@RequiredArgsConstructor
public class DataSeederSalePost implements CommandLineRunner {

    private final SalePostRepository salePostRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        List<SalePost> salePostList = new ArrayList<>();

        User testuser = userRepository.findById(1L).orElseThrow(null);
        Category testcategory = categoryRepository.findById(1L).orElse(null);
        Category testcategory2 = categoryRepository.findById(2L).orElse(null);
        Category testcategory3 = categoryRepository.findById(3L).orElse(null);

        for (int i = 0; i < 10000; i++) {
            SalePost post = SalePost.builder()
                .user(testuser)
                .title("cat " + i)
                .category(testcategory)
                .description("부송이" + i)
                .goods("cat food")
                .stock((long) Math.random())
                .views((long) Math.random() * 10)
                .price((long) Math.random() * 1000)
                .status(true)
                .build();
            salePostList.add(post);
        }

        for (int i = 0; i < 10000; i++) {
            SalePost post = SalePost.builder()
                .user(testuser)
                .title("cat " + i)
                .category(testcategory2)
                .description("부송이" + i)
                .goods("dog food")
                .stock((long) Math.random())
                .views((long) Math.random() * 10)
                .price((long) Math.random() * 1000)
                .status(true)
                .build();
            salePostList.add(post);
        }

        for (int i = 0; i < 10000; i++) {
            SalePost post = SalePost.builder()
                .user(testuser)
                .title("dog " + i)
                .category(testcategory3)
                .description("부송이" + i)
                .goods("cat toy")
                .stock((long) Math.random())
                .views((long) Math.random() * 10)
                .price((long) Math.random() * 1000)
                .status(true)
                .build();
            salePostList.add(post);
        }

        for (int i = 0; i < 10000; i++) {
            SalePost post = SalePost.builder()
                .user(testuser)
                .title("dog " + i)
                .category(testcategory)
                .description("부송이" + i)
                .goods("dog toy")
                .stock((long) Math.random())
                .views((long) Math.random() * 10)
                .price((long) Math.random() * 1000)
                .status(true)
                .build();
        }

        for (int i = 0; i < 10000; i++) {
            SalePost post = SalePost.builder()
                .user(testuser)
                .title("dog " + i)
                .category(testcategory)
                .description("부송이" + i)
                .goods("toy food")
                .stock((long) Math.random())
                .views((long) Math.random() * 10)
                .price((long) Math.random() * 1000)
                .status(true)
                .build();
            salePostList.add(post);
        }

        salePostRepository.saveAll(salePostList);

    }

}
