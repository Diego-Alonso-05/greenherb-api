package com.greenherb.greenherb_api.controller;

import com.greenherb.greenherb_api.dto.LoginRequest;
import com.greenherb.greenherb_api.dto.LoginResponse;
import com.greenherb.greenherb_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String token = authService.login(request);

        return new LoginResponse(token);
    }
}