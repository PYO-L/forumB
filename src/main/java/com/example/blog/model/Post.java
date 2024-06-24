package com.example.blog.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private List<String> contents;
    private LocalDateTime createdAt;
    private int views;
    private int likes;

    @ElementCollection
    private List<String> imageUrls;

    @ElementCollection
    private Set<Long> viewedUsers = new HashSet<>();

    @ElementCollection
    private Set<Long> likedUsers = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public Set<Long> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<Long> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Set<Long> getViewedUsers() {
        return viewedUsers;
    }

    public void setViewedUsers(Set<Long> viewedUsers) {
        this.viewedUsers = viewedUsers;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getters and Setters
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}