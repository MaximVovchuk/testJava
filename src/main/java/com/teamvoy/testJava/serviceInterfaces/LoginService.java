package com.teamvoy.testJava.serviceInterfaces;

import com.teamvoy.testJava.dto.AuthenticationDTO;
import com.teamvoy.testJava.exceptions.Status420WrongPasswordException;

public interface LoginService {
    String login(AuthenticationDTO authenticationDTO) throws Status420WrongPasswordException;
}
