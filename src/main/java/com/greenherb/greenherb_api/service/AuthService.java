package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.dto.LoginRequest;
import com.greenherb.greenherb_api.model.User;
import com.greenherb.greenherb_api.repository.UserRepository;
import com.greenherb.greenherb_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}