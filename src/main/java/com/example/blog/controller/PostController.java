package com.example.blog.controller;

import com.example.blog.dto.PostDTO;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<PostDTO> getAllPosts(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable("id") Long id) {

        return postService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postService.createPost(postDTO,user);
    }

    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable("id") Long id, @RequestBody PostDTO postDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return postService.updatePost(id, postDTO, user);
    }

    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable("id") Long id,Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return postService.deletePost(id,user);
    }
}
