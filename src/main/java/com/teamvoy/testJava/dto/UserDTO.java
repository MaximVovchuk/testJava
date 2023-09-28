package com.teamvoy.testJava.dto;

import com.teamvoy.testJava.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String role;
}
