package com.teamvoy.testJava.util;


import com.teamvoy.testJava.models.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Long> {

    @Override
    public Long convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Long code) {
        if (code == null) {
            return null;
        }
        return Stream.of(Role.values())
                .filter(c -> code.equals(c.getCode()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}