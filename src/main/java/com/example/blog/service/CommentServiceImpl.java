package com.example.blog.service;

import com.example.blog.dto.CommentDTO;
import com.example.blog.dto.UserDTO;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = convertToEntity(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getPost().getId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to the post");
        }
        if(comment.getUser().getId().equals(currentUser.getId())) {
            comment.setContent(commentDTO.getContent());
            Comment updatedComment = commentRepository.save(comment);
            return convertToDTO(updatedComment);
        }
        throw new RuntimeException("User is not authorized to update this comment");
    }

    public boolean deleteComment(Long commentId, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if(comment.getUser().getId().equals(currentUser.getId())){
            commentRepository.delete(comment);
            return true;
        }
        throw new RuntimeException("User is not authorized to delete this comment");
    }


    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        if(comment.getUser() != null) {
            commentDTO.setUsername(comment.getUser().getUsername());
        }
        return commentDTO;
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        return comment;
    }
}
