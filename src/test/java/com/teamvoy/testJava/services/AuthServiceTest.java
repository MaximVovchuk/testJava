package com.teamvoy.testJava.services;

import com.teamvoy.testJava.models.User;
import com.teamvoy.testJava.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername_UserExists() {
        String userEmail = "test@example.com";
        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findByEmail(userEmail)).thenReturn(user);

        UserDetails userDetails = authService.loadUserByUsername(userEmail);

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
    }


    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String userEmail = "nonexistent@example.com";

        when(userRepository.findByEmail(userEmail)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername(userEmail));
    }
}