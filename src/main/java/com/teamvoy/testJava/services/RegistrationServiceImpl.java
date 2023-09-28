package com.teamvoy.testJava.services;

import com.teamvoy.testJava.dto.UserDTO;
import com.teamvoy.testJava.exceptions.Status421EmailBusyException;
import com.teamvoy.testJava.models.Role;
import com.teamvoy.testJava.models.User;
import com.teamvoy.testJava.repositories.UserRepository;
import com.teamvoy.testJava.serviceInterfaces.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String baseRegister(UserDTO userDTO) throws Status421EmailBusyException {
        User user = convertToUser(userDTO);
        validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getEmail();
    }

    private void validate(User user) throws Status421EmailBusyException {
        User fromDB = userRepository.findByEmail(user.getEmail());
        if (fromDB != null) {
            throw new Status421EmailBusyException();
        }
    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setRole(Role.valueOf(userDTO.getRole()));
        return user;
    }
}
