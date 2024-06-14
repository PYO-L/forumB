package com.example.blog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${file.upload-dir}")
    private String uploadDirectory; // application.properties에서 설정된 업로드 디렉토리 경로

    public String saveImage(MultipartFile file) {
        try {
            // 업로드할 디렉토리 경로 설정
            Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();

            // 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일명 생성 (UUID를 사용하여 고유한 파일명 생성)
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 파일 저장 경로 설정
            Path filePath = uploadPath.resolve(fileName).normalize();

            // 파일 저장
            Files.copy(file.getInputStream(), filePath);

            // 클라이언트가 접근할 수 있는 URL 반환 (예: http://localhost:8080/images/파일명)
            return fileName; // 실제 URL은 애플리케이션의 구성에 따라 달라질 수 있습니다.
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}