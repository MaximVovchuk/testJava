package com.teamvoy.testJava.services;

import com.teamvoy.testJava.dto.UserDTO;
import com.teamvoy.testJava.exceptions.Status421EmailBusyException;
import com.teamvoy.testJava.models.Role;
import com.teamvoy.testJava.models.User;
import com.teamvoy.testJava.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    public void testBaseRegister_SuccessfulRegistration() throws Status421EmailBusyException {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("password");
        userDTO.setRole("CLIENT");

        User user = new User();
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        String registeredEmail = registrationService.baseRegister(userDTO);

        // Assert
        assertEquals("john@example.com", registeredEmail);

        // Verify that userRepository.save was called with the expected user object.
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(Role.CLIENT, savedUser.getRole());
    }

    @Test
    public void testBaseRegister_EmailAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("password");
        userDTO.setRole("CLIENT");

        when(userRepository.findByEmail("john@example.com")).thenReturn(new User()); // User already exists.

        // Act
        assertThrows(Status421EmailBusyException.class, () -> registrationService.baseRegister(userDTO));
    }

    @Test
    public void testBaseRegister_InvalidUserData() {
        // Arrange
        UserDTO userDTO = new UserDTO(); // Empty userDTO

        // Act
        assertThrows(NullPointerException.class, () -> registrationService.baseRegister(userDTO));
    }
}