package com.greenherb.greenherb_api.config;

import com.greenherb.greenherb_api.model.Role;
import com.greenherb.greenherb_api.model.User;
import com.greenherb.greenherb_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {

            User user = new User();

            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole(Role.ADMIN);

            userRepository.save(user);

            System.out.println("Admin user created");
        }
    }
}