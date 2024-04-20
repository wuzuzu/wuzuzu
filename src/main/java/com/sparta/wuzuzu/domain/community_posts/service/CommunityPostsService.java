package com.sparta.wuzuzu.domain.community_posts.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.sparta.wuzuzu.domain.common.image.service.ImageService;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityElasticResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostElasticListResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostListRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostListResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostRequest;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityCategory;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPost;
//import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPostDocument;
//import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostDocumentRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityPostRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CommunityCategoryRepository;
//import com.sparta.wuzuzu.domain.community_posts.repository.CustomCommunityPostDocumentRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CustomCommunityPostElasticRepository;
import com.sparta.wuzuzu.domain.community_posts.repository.CustomCommunityPostRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.global.dto.request.ListRequest;
import com.sparta.wuzuzu.global.exception.ValidateUserException;
import com.sparta.wuzuzu.global.util.PagingUtil;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityPostsService {

    private final CommunityPostRepository communityPostRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final CustomCommunityPostRepository customCommunityPostRepository;
//    private final CommunityPostDocumentRepository communityPostDocumentRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final ImageService imageService;
//    private final CustomCommunityPostDocumentRepository customCommunityPostDocumentRepository;
    private final CustomCommunityPostElasticRepository communityPostElasticRepository;
    public CommunityPostResponse saveCommunityPosts(CommunityPostRequest communityPostRequest,
        User user) {

        CommunityCategory communityCategory = communityCategoryRepository.findByNameEquals(
                communityPostRequest.getCategoryName())
            .orElseThrow();
        if (communityCategory.getStatus() == false) {
            throw new NoSuchElementException();
        }

        communityPostRepository.save(new CommunityPost(
                communityPostRequest.getTitle(),
                communityCategory,
                communityPostRequest.getContent(),
                user
            )
        );
        return CommunityPostResponse.builder().
            title(communityPostRequest.getTitle()).
            categoryName(communityPostRequest.getCategoryName()).
            username(user.getUserName()).
            contents(communityPostRequest.getContent()).
            build();

    }

    @Transactional
    public CommunityPostResponse updateCommunityPosts(CommunityPostRequest communityPostRequest,
        Long userId, Long communityPostsId) {
        CommunityPost post = communityPostRepository.findById(communityPostsId).orElseThrow();
        post.validateUser(userId);
        post.updateCommunityPosts(
            communityPostRequest.getTitle(),
            communityCategoryRepository.findByNameEquals(communityPostRequest.getCategoryName())
                .orElseThrow(),
            communityPostRequest.getContent());
        return CommunityPostResponse.builder().
            title(post.getTitle()).
            categoryName(post.getCategory().getName()).
            username(post.getUser().getUserName()).
            contents(post.getContent()).
            build();

    }


    public CommunityPostListResponse searchCommunityPosts(CommunityPostListRequest request) {
        if (request.getColumn() == null || request.getColumn().isEmpty()) {
            request.setColumn("createdAt");//조회 기준 컬럼 입력 없을 경우 날짜순을 기본으로 지정
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(),
            Sort.by(request.getSortDirection(), request.getColumn()));
        Page<CommunityPostResponse> postsPage = customCommunityPostRepository.findByConditions(
            request.getKeyword(), request.getCategoryName(), pageable);

        PagingUtil pagingUtil = new PagingUtil(postsPage.getTotalElements(),
            postsPage.getTotalPages(), request.getPage(), request.getPageSize());

        return CommunityPostListResponse.builder()
            .postList(postsPage.getContent())
            .pagingUtil(pagingUtil)
            .build();
    }

    public CommunityPostElasticListResponse searchPostByKeyword(String keyword, ListRequest request) {
        if (request.getColumn() == null || request.getColumn().isEmpty()) {
            request.setColumn("createdAt");//조회 기준 컬럼 입력 없을 경우 날짜순을 기본으로 지정
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(),
            Sort.by(request.getSortDirection(), request.getColumn()));
        Page<CommunityElasticResponse> postsPage = communityPostElasticRepository.findByTitleContaining(keyword, pageable);

        PagingUtil pagingUtil = new PagingUtil(postsPage.getTotalElements(),
            postsPage.getTotalPages(), request.getPage(), request.getPageSize());

        return CommunityPostElasticListResponse.builder()
            .postList(postsPage.getContent())
            .pagingUtil(pagingUtil)
            .build();
    }



//    public CommunityPostSearchListResponse searchPostByKeyword(String keyword, ListRequest request) {
//        if (request.getColumn() == null || request.getColumn().isEmpty()) {
//            request.setColumn("createdAt");//조회 기준 컬럼 입력 없을 경우 날짜순을 기본으로 지정
//        }
//
//        try {
//            Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(), Sort.by(request.getSortDirection(), request.getColumn()));
//            Page<CommunityPostDocument> postsPage = communityPostDocumentRepository.findByTitleContaining(keyword, pageable);
//            List<CommunityPostSearchResponse> searchResponses = postsPage.getContent().stream()
//                .map(CommunityPostSearchResponse::new)
//                .collect(Collectors.toList());
//            PagingUtil pagingUtil = new PagingUtil(postsPage.getTotalElements(), postsPage.getTotalPages(), request.getPage(), request.getPageSize());
//            return CommunityPostSearchListResponse.builder()
//                .postList(searchResponses)
//                .pagingUtil(pagingUtil)
//                .build();
//        } catch (Exception e) {
//            // Log the exception or handle it accordingly
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public CommunityPostSearchListResponse searchPostByKeyword(String keyword, ListRequest request) throws IOException {
//        SearchResponse<CommunityPostDocument> response = elasticsearchClient.search(s -> s
//                .index("community_posts")
//                .query(q -> q
//                    .match(m -> m
//                        .field("title")
//                        .query(keyword)
//                    )
//                )
//                .sort(so -> so
//                    .field(f -> f
//                        .field(request.getColumn().isEmpty() ? "createdAt" : request.getColumn())
//                        .order(request.getSortDirection().equals("asc") ? SortOrder.Asc : SortOrder.Desc)
//                    )
//                )
//                .from(request.getPage() * request.getPageSize())
//                .size(request.getPageSize()),
//            CommunityPostDocument.class
//        );
//
//        List<CommunityPostSearchResponse> searchResponses = response.hits().hits().stream()
//            .map(hit -> new CommunityPostSearchResponse(hit.source()))
//            .collect(Collectors.toList());
//
//        PagingUtil pagingUtil = new PagingUtil(
//            response.hits().total().value(),
//            response.hits().hits().size(),
//            request.getPage(),
//            request.getPageSize()
//        );
//
//        return CommunityPostSearchListResponse.builder()
//            .postList(searchResponses) // Assuming CommunityPostListResponse can accept a list of CommunityPostSearchResponse
//           .pagingUtil(pagingUtil)
//           .build();
//
//    }


    @Transactional
    public CommunityPostResponse getDetail(Long communitypostsId) {
        CommunityPost post = communityPostRepository.findById(communitypostsId)
            .orElseThrow(() -> new NoSuchElementException("해당 글을 찾을 수 없습니다."));
        post.increaseViews();
        return CommunityPostResponse.builder().
            title(post.getTitle()).
            username(post.getUser().getUserName()).
            contents(post.getContent()).
            views(post.getViews()).
            categoryName(post.getCategory().getName()).
            comments(post.getCommentList().size()).
            build();
    }

    @Transactional
    public void deleteCommunityPost(Long communityPostsID, User user) {
        communityPostRepository.findById(communityPostsID)
            .filter(communityPosts -> user.getUserId().equals(communityPosts.getUser().getUserId()))
            .orElseThrow(ValidateUserException::new); // 사용자 ID가 게시글 작성자의 ID와 일치하지 않을 경우 예외 발생

        communityPostRepository.deleteById(communityPostsID); // 게시글 삭제
    }


    @Transactional
    public void uploadImage(User user, Long communityPostId, List<MultipartFile> imageFiles) throws IOException {
        CommunityPost communityPost = checkCommunityPost(user, communityPostId);

        for (MultipartFile imageFile : imageFiles) {
            imageService.createImage(imageFile, communityPost);
        }
    }

    public void deleteImage(User user, Long communityPostId, String key){
        CommunityPost communityPost = checkCommunityPost(user, communityPostId);
        imageService.deleteImage(key, communityPost);
    }

    private CommunityPost checkCommunityPost(User user, Long communityPostId){
        CommunityPost communityPost = communityPostRepository.findById(communityPostId).orElseThrow(
            () -> new IllegalArgumentException("post is empty.")
        );

        if(!user.getUserId().equals(communityPost.getUser().getUserId())){
            throw new IllegalArgumentException("post is not yours");
        }

        return communityPost;
    }
}
