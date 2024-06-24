package com.example.blog.service;

import com.example.blog.dto.PostDTO;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public PostDTO likePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if(post.getLikedUsers().add(user.getId())) {
            post.setLikes(post.getLikes() + 1); // 좋아요 수 증가
            postRepository.save(post);
        }
        return convertToDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getViewedUsers().add(user.getId())) {
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
        }
        return convertToDTO(post);
    }

    @Override
    public PostDTO createPost(String title, List<String> contents, LocalDateTime createdAt, List<MultipartFile> images, User user) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        post.setCreatedAt(createdAt);
        post.setUser(user);

        try {
            if (images != null && !images.isEmpty()) {
                List<String> imageUrls = imageService.saveImage(images); // 이미지 저장 후 파일명 반환
                post.setImageUrls(imageUrls); // 저장된 이미지 URL을 Post 객체에 설정
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to store image", e);
        }

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO,User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if(post.getUser().getId().equals(user.getId())) {
            String[] ignoreProperties = {"id", "user"};
            BeanUtils.copyProperties(postDTO, post, ignoreProperties);
            post = postRepository.save(post);
            return convertToDTO(post);
        }

        return null;
    }

    @Override
    public boolean deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getUser().getId().equals(user.getId())) {
            postRepository.delete(post);
            return true;
        }
        return false;
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setCreatedAt(post.getCreatedAt()); // 생성일 설정
        postDTO.setViews(post.getViews());
        postDTO.setImageUrls(post.getImageUrls());
        postDTO.setLikes(post.getLikes());
        if(post.getUser() != null){
            postDTO.setUsername(post.getUser().getUsername());
        }
        return postDTO;
    }

    private Post convertToEntity(PostDTO postDTO) {
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        return post;
    }
}