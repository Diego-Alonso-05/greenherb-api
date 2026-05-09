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

        createUser("admin", "1234", Role.ADMIN);

        createUser("technician", "1234", Role.TECHNICIAN);

        createUser("responsible", "1234", Role.RESPONSIBLE);
    }

    private void createUser(
            String username,
            String password,
            Role role
    ) {

        if (userRepository.findByUsername(username).isEmpty()) {

            User user = new User();

            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);

            userRepository.save(user);

            System.out.println(username + " created");
        }
    }
}