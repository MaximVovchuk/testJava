package com.teamvoy.testJava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {
    private String email;
    private String password;
}
