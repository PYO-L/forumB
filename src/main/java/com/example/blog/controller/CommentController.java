package com.example.blog.controller;

import com.example.blog.dto.CommentDTO;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<CommentDTO> getAllCommentsByPostId(@PathVariable("postId") Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return commentService.createComment(postId, commentDTO, user);
    }

    @PutMapping("/{commentId}")
    public CommentDTO updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return commentService.updateComment(postId, commentId, commentDTO, user);
    }

    @DeleteMapping("/{commentId}")
    public boolean deleteComment(@PathVariable("commentId") Long commentId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        return commentService.deleteComment(commentId, user);
    }
}
