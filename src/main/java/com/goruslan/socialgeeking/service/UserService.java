package com.goruslan.socialgeeking.service;

import com.goruslan.socialgeeking.domain.User;
import com.goruslan.socialgeeking.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

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

    public User register(User user) {
        try {
            String secret = "{bcrypt}" + encoder.encode(user.getPassword());
            user.setPassword(secret);
            user.setConfirmPassword(secret);
            user.setEnabled(true);
            user.addRole(roleService.findByName("ROLE_USER"));
            save(user);
        }catch (Exception e){
            logger.debug("Failed to register " + e.getMessage());
        }
        return user;
    }

    public User save(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e){
            logger.debug("Failed to save user " + e.getMessage());
        }
        return user;
    }


    @Transactional
    public void saveUsers(User... users) {
        for(User user : users) {
            logger.info("Saving User: " + user.getEmail());
            userRepository.save(user);
        }
    }

}
