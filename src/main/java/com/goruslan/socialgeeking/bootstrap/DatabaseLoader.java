package com.goruslan.socialgeeking.bootstrap;

import com.goruslan.socialgeeking.domain.*;
import com.goruslan.socialgeeking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private LocationRepository locationRepository;


    private Map<String,User> users = new HashMap<>();


    public DatabaseLoader(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, RoleRepository roleRepository, LocationRepository locationRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) {
        addData();
        Map<String,String> posts = new HashMap<>();

        posts.put("Build a Secure Progressive Web App With Spring Boot and React","https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo");
        posts.put("Building Your First Spring Boot Web Application - DZone Java","https://dzone.com/articles/building-your-first-spring-boot-web-application-ex");
        posts.put("Building Microservices with Spring Boot Fat (Uber) Jar","https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/");

        posts.forEach((k,v) -> {
            User u1 = users.get("user@gmail.com");
            Post post = new Post(k, v);
            if(k.startsWith("Build")) {
                post.setUser(u1);
            }
            postRepository.save(post);

            Comment spring = new Comment("Thank you for this link related to Spring Boot. I love it, great post!", post);
            Comment security = new Comment("I love that you're talking about Spring Security", post);
            Comment pwa = new Comment("What is this Progressive Web App thing all about? PWAs sound really cool.", post);
            Comment comments[] = {spring,security,pwa};
            for(Comment comment : comments) {
                commentRepository.save(comment);
                post.addComment(comment);
            }

        });

        long postCount = postRepository.count();
        System.out.println("Number of posts in the database: " + postCount );
    }


    private void addData() {
        Location location1 = new Location("Sylhet");
        locationRepository.save(location1);

        Location location2 = new Location("Bandarban");
        locationRepository.save(location2);

        Location location3 = new Location("Khulna");
        locationRepository.save(location3);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);

        User user = new User("user@gmail.com",secret,true,"John Doe","Bachelors in Computer Science",
                "JoDoe");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        userRepository.save(user);
        users.put("user@gmail.com", user);
    }
}
