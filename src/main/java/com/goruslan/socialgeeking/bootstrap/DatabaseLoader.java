package com.goruslan.socialgeeking.bootstrap;

import com.goruslan.socialgeeking.domain.*;
import com.goruslan.socialgeeking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private LocationRepository locationRepository;


    private Map<String,User> users = new HashMap<>();


    public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) {
        addData();
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
