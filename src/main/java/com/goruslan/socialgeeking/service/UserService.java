package com.goruslan.socialgeeking.service;

import com.goruslan.socialgeeking.domain.User;
import com.goruslan.socialgeeking.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);
        user.setEnabled(true);
        user.addRole(roleService.findByName("ROLE_USER"));
        save(user);
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String Username) {
        return userRepository.findByUsername(Username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
