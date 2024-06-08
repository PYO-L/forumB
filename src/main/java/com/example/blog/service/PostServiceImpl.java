package com.example.blog.service;

import com.example.blog.dto.PostDTO;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.map(this::convertToDTO).orElse(null);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, User user) {
        Post post = convertToEntity(postDTO);
        post.setUser(user);
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