package com.example.blog.service;

import com.example.blog.dto.CommentDTO;
import com.example.blog.model.User;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllCommentsByPostId(Long postId);
    CommentDTO createComment(Long postId, CommentDTO commentDTO, User user);
    CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO, User currentUser);
    boolean deleteComment(Long commentId, User user);
}