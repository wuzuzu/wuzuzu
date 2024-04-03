package com.sparta.wuzuzu.domain.common.image.service;

import com.sparta.wuzuzu.domain.common.image.entity.Image;
import com.sparta.wuzuzu.domain.common.image.repository.ImageRepository;
import com.sparta.wuzuzu.domain.sale_post.entity.SalePost;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${defaultImage.path}")
    private String defaultImagePath;

    @Value("${upload.path}")
    private String uploadPath;

    private final S3Service s3Service;

    private final ImageRepository imageRepository;

    @Transactional
    public void createImage(MultipartFile file, SalePost salePost) throws IOException {
        String imageName = getImageName(file);

        imageRepository.save(new Image(imageName, salePost));
    }

    private String getImageName(MultipartFile file) throws IOException {
        if (file != null) {
            // UUID.randomUUID() : UUID 클래스를 이용해 시간과 공간을 기반으로 128비트의 고유한 식별자 생성
            // file.getOriginalFilename() : 클라이언트가 업로드한 파일의 원래 파일 이름 반환
            String originalFileName = UUID.randomUUID() + file.getOriginalFilename();
            return s3Service.upload(file, originalFileName);
        }
        return defaultImagePath;
    }
}