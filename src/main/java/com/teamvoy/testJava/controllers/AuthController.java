package com.teamvoy.testJava.controllers;

import com.teamvoy.testJava.dto.AuthenticationDTO;
import com.teamvoy.testJava.dto.UserDTO;
import com.teamvoy.testJava.exceptions.Status420WrongPasswordException;
import com.teamvoy.testJava.exceptions.Status421EmailBusyException;
import com.teamvoy.testJava.serviceInterfaces.LoginService;
import com.teamvoy.testJava.serviceInterfaces.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Auth")
public class AuthController {
    private final LoginService loginService;
    private final RegistrationService registrationService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws Status420WrongPasswordException {
        return ResponseEntity.ok(loginService.login(new AuthenticationDTO(email, password)));
    }

    @PostMapping("/register")
    public ResponseEntity<String> baseRegister(@RequestBody UserDTO userDTO) throws Status421EmailBusyException {
        return ResponseEntity.ok(registrationService.baseRegister(userDTO));
    }
}
