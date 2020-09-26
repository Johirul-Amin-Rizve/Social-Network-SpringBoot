package com.goruslan.socialgeeking.service;


import com.goruslan.socialgeeking.domain.Post;
import com.goruslan.socialgeeking.domain.User;
import com.goruslan.socialgeeking.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByPrivacy(String privacy) {
        return postRepository.findAllByPrivacy(privacy);
    }

    public List<Post> findAllByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    public List<Post> findAllByPrivacyAndUser(String privacy, User user) {
        return postRepository.findAllByPrivacyAndUser(privacy, user);
    }

    public List<Post> findAllByPrivacyAndNotUser(String privacy, User user) {
        return postRepository.findAllByPrivacyAndNotUser(privacy, user);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

//    public void updatePost(Post post) {
//         postRepository.updatePost(post.getPrivacy(), post.getTitle(), post.getLocation(), post.getId());
//    }

}
