package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.dto.LoginRequest;
import com.greenherb.greenherb_api.model.Role;
import com.greenherb.greenherb_api.model.User;
import com.greenherb.greenherb_api.repository.UserRepository;
import com.greenherb.greenherb_api.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_withValidCredentials_returnsToken() {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("1234");

        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("1234", "encodedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken("admin", Role.ADMIN))
                .thenReturn("fake-jwt-token");

        String token = authService.login(request);

        assertEquals("fake-jwt-token", token);

        verify(userRepository).findByUsername("admin");
        verify(passwordEncoder).matches("1234", "encodedPassword");
        verify(jwtService).generateToken("admin", Role.ADMIN);
    }

    @Test
    void login_withInvalidUsername_throwsException() {

        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("1234");

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findByUsername("unknown");
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any(), any());
    }

    @Test
    void login_withWrongPassword_throwsException() {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");
        user.setRole(Role.ADMIN);

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid password", exception.getMessage());

        verify(userRepository).findByUsername("admin");
        verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
        verify(jwtService, never()).generateToken(any(), any());
    }
}