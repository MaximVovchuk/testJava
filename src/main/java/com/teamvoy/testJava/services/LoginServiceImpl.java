package com.teamvoy.testJava.services;


import com.teamvoy.testJava.config.JWTUtil;
import com.teamvoy.testJava.config.JwtUser;
import com.teamvoy.testJava.dto.AuthenticationDTO;
import com.teamvoy.testJava.exceptions.Status420WrongPasswordException;
import com.teamvoy.testJava.models.Role;
import com.teamvoy.testJava.serviceInterfaces.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public String login(AuthenticationDTO authenticationDTO) throws Status420WrongPasswordException {
        JwtUser userDetails;
        userDetails = authService.loadUserByUsername(authenticationDTO.getEmail());
        if (!passwordEncoder.matches(authenticationDTO.getPassword(), userDetails.getPassword())) {
            throw new Status420WrongPasswordException();
        }
        return jwtUtil.generateToken(userDetails.getUsername(), (Collection<Role>) userDetails.getAuthorities());
    }
}
