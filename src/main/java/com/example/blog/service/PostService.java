package com.example.blog.service;

import com.example.blog.dto.PostDTO;
import com.example.blog.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    PostDTO likePost(Long id, User user);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id, User user);
    PostDTO createPost(String title, List<String> contents, LocalDateTime createdAt, List<MultipartFile> images, User user);
    PostDTO updatePost(Long id, PostDTO postDTO, User user);
    boolean deletePost(Long id, User user);


}