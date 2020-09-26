package com.goruslan.socialgeeking.service;

import com.goruslan.socialgeeking.domain.User;
import com.goruslan.socialgeeking.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        encoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User register(User user) {
        try {
            String secret = "{bcrypt}" + encoder.encode(user.getPassword());
            user.setPassword(secret);
            user.setEnabled(true);
            user.setConfirmPassword(secret);
            user.addRole(roleService.findByName("ROLE_USER"));
            save(user);
            logger.debug(user.toString());
        } catch (Exception e) {
            logger.debug("Failed to create Booking " + e.getMessage());
        }
        return user;
    }

    @Transactional
    public User save(User user) {
        try {
            userRepository.save(user);
            logger.debug(user.toString());
        } catch (Exception e) {
            logger.debug("Failed to create Booking " + e.getMessage());
        }
        return user;
    }
}
