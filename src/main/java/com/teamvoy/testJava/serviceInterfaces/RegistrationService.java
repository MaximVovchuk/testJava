package com.teamvoy.testJava.serviceInterfaces;

import com.teamvoy.testJava.dto.UserDTO;
import com.teamvoy.testJava.exceptions.Status421EmailBusyException;

public interface RegistrationService {
    String baseRegister(UserDTO userDTO) throws Status421EmailBusyException;
}
