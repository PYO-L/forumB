package com.example.blog.service;

import com.example.blog.dto.PostDTO;
import com.example.blog.model.User;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    PostDTO createPost(PostDTO postDTO, User user);
    PostDTO updatePost(Long id, PostDTO postDTO, User user);
    boolean deletePost(Long id, User user);
}