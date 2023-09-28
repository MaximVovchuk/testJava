package com.teamvoy.testJava.services;

import com.teamvoy.testJava.config.JwtUser;
import com.teamvoy.testJava.models.User;
import com.teamvoy.testJava.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public JwtUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new JwtUser(user);
    }
}
