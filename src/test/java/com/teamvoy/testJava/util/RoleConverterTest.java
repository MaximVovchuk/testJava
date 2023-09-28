package com.teamvoy.testJava.util;

import com.teamvoy.testJava.models.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleConverterTest {

    @InjectMocks
    RoleConverter roleConverter;


    @Test
    public void testConvertToDatabaseColumn() {
        Role role = Role.CLIENT;
        Long expectedCode = role.getCode();

        Long result = roleConverter.convertToDatabaseColumn(role);

        assertEquals(expectedCode, result);
    }

    @Test
    public void testConvertToDatabaseColumnWithNullRole() {
        Long result = roleConverter.convertToDatabaseColumn(null);

        assertNull(result);
    }

    @Test
    public void testConvertToEntityAttribute() {
        Long code = Role.CLIENT.getCode();

        Role result = roleConverter.convertToEntityAttribute(code);

        assertEquals(Role.CLIENT, result);
    }

    @Test
    public void testConvertToEntityAttributeWithNullCode() {
        Role result = roleConverter.convertToEntityAttribute(null);

        assertNull(result);
    }

    @Test
    public void testConvertToEntityAttributeWithInvalidCode() {
        Long invalidCode = 999L;

        assertThrows(IllegalArgumentException.class, () -> roleConverter.convertToEntityAttribute(invalidCode));
    }
}