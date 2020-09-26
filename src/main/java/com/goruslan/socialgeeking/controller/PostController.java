package com.goruslan.socialgeeking.controller;

import com.goruslan.socialgeeking.domain.Comment;
import com.goruslan.socialgeeking.domain.Post;
import com.goruslan.socialgeeking.domain.User;
import com.goruslan.socialgeeking.repository.CommentRepository;
import com.goruslan.socialgeeking.repository.UserRepository;
import com.goruslan.socialgeeking.service.LocationService;
import com.goruslan.socialgeeking.service.PostService;
import com.goruslan.socialgeeking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private PostService postService;
    private LocationService locationService;
    private CommentRepository commentRepository;
    private UserService userService;


    public PostController(PostService postService,
                          LocationService locationService,
                          CommentRepository commentRepository,
                          UserService userService) {
        this.postService = postService;
        this.locationService = locationService;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String list(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (!username.equals("anonymousUser") ){
            User user = userService.findByUsername(username);
            model.addAttribute("otherPublicPosts", postService.findAllByPrivacyAndNotUser("public", user));
        }
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("publicPosts", postService.findByPrivacy("public"));
        return "post/list";
    }

    @GetMapping("/post/{id}")
    public String read(@PathVariable Long id, Model model) {
        model.addAttribute("locations", locationService.findAll());
        Optional<Post> post = postService.findById(id);
        if( post.isPresent() ) {
            Post currentPost = post.get();
            Comment comment = new Comment();
            comment.setPost(currentPost);
            model.addAttribute("comment", comment);
            model.addAttribute("post", currentPost);
            model.addAttribute("success", model.containsAttribute("success"));
            return "post/view";

        } else {
            return "redirect:/ ";
        }
    }

    @GetMapping("/post/submit")
    public String newPostForm(Model model){
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("post", new Post());
        return "post/submit";
    }

    @PostMapping("/post/submit")
    public String createPost(@Valid Post post, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("locations", locationService.findAll());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }

        User user = userService.findByUsername(username);
        post.setUser(user);

        if( bindingResult.hasErrors()){
            logger.info("Validation error while submitting a new post.");
            model.addAttribute("post", post);
            return "post/submit";
        } else {
            postService.save(post);
            logger.info("New Post was saved successfully.");
            redirectAttributes
                    .addAttribute("id", post.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/post/{id}";

        }
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/post/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            logger.info("There was a problem adding a new comment.");
        } else {
            commentRepository.save(comment);
            logger.info("New comment was saved.");
        }
        return "redirect:/post/" + comment.getPost().getId();
    }

}
