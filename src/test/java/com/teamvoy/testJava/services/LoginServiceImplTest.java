package com.teamvoy.testJava.services;

import com.teamvoy.testJava.config.JWTUtil;
import com.teamvoy.testJava.config.JwtUser;
import com.teamvoy.testJava.dto.AuthenticationDTO;
import com.teamvoy.testJava.exceptions.Status420WrongPasswordException;
import com.teamvoy.testJava.models.Role;
import com.teamvoy.testJava.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {
    @InjectMocks
    private LoginServiceImpl loginService;
    @Mock
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTUtil jwtUtil;

    @Test
    public void testLogin_SuccessfulLogin() throws Status420WrongPasswordException {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "password");

        JwtUser userDetails = new JwtUser(new User(1L, "Max", "test@example.com", "password", Role.CLIENT));

        when(authService.loadUserByUsername(authenticationDTO.getEmail())).thenReturn(userDetails);
        when(passwordEncoder.matches(authenticationDTO.getPassword(), userDetails.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(userDetails.getUsername(), (Collection<Role>) userDetails.getAuthorities())).thenReturn("token");

        String token = loginService.login(authenticationDTO);

        assertEquals("token", token);
    }

    @Test
    public void testLogin_WrongPassword() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("test@example.com", "wrongPassword");

        JwtUser userDetails = new JwtUser(new User(1L, "Max", "test@example.com", "password", Role.CLIENT));

        when(authService.loadUserByUsername(authenticationDTO.getEmail())).thenReturn(userDetails);
        when(passwordEncoder.matches(authenticationDTO.getPassword(), userDetails.getPassword())).thenReturn(false);

        assertThrows(Status420WrongPasswordException.class, () -> loginService.login(authenticationDTO));
    }
}