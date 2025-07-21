package org.example.billing_system.business.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserLoginDTOTest {

    @Test
    void constructor_WithValidParameters_ShouldCreateDTO() {
        // Act
        UserLoginDTO dto = new UserLoginDTO("testuser", "password123");

        // Assert
        assertEquals("testuser", dto.getUsername());
        assertEquals("password123", dto.getPassword());
    }

    @Test
    void isValid_WithValidCredentials_ShouldReturnTrue() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("testuser", "password123");

        // Act & Assert
        assertTrue(dto.isValid());
    }

    @Test
    void isValid_WithNullUsername_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO(null, "password123");

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyUsername_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("", "password123");

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithWhitespaceUsername_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("   ", "password123");

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithNullPassword_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("testuser", null);

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyPassword_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("testuser", "");

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithWhitespacePassword_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("testuser", "   ");

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("olduser", "oldpassword");

        // Act
        dto.setUsername("newuser");
        dto.setPassword("newpassword");

        // Assert
        assertEquals("newuser", dto.getUsername());
        assertEquals("newpassword", dto.getPassword());
        assertTrue(dto.isValid());
    }

    @Test
    void isValid_WithBothFieldsNull_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO(null, null);

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithBothFieldsEmpty_ShouldReturnFalse() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO("", "");

        // Act & Assert
        assertFalse(dto.isValid());
    }
}