package com.sparta.wuzuzu.domain.common.image.service;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${defaultImage.path}")
    private String defaultImagePath;

    @Value("${upload.path}")
    private String uploadPath;

    private final S3Service s3Service;

    public String createImageName(MultipartFile file) throws IOException {
        return getImageName(file);
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