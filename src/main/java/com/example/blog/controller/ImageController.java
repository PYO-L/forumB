//package com.example.blog.controller;
//
//import com.example.blog.service.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//public class ImageController {
//
//    private final ImageService imageService;
//    private final String uploadDirectory; // 업로드 디렉토리 경로
//
//    @Autowired
//    public ImageController(ImageService imageService, @Value("${file.upload-dir}") String uploadDirectory) {
//        this.imageService = imageService;
//        this.uploadDirectory = uploadDirectory;
//    }
//
//    @GetMapping("/images/{fileName:.+}")
//    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
//        Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
//        Resource resource;
//        try {
//            resource = new UrlResource(filePath.toUri());
//        } catch (IOException e) {
//            throw new RuntimeException("File not found: " + fileName, e);
//        }
//
//        // Content-Type을 이미지로 설정
//        MediaType mediaType;
//        try {
//            mediaType = MediaType.valueOf(Files.probeContentType(filePath));
//        } catch (IOException e) {
//            mediaType = MediaType.APPLICATION_OCTET_STREAM;
//        }
//
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
//}
