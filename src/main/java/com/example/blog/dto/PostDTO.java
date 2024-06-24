package com.example.blog.dto;

import com.example.blog.model.User;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {
    private Long id;
    private String title;
    private List<String> contents;
    private LocalDateTime createdAt;
    private int views;
    private List<CommentDTO> comments = new ArrayList<>();
    private UserDTO user;
    private String username;
    private List<String> imageUrls;

    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }



    public PostDTO() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    // Getters and Setters
}