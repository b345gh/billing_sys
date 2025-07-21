package org.example.billing_system.business.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationDTOTest {

    @Test
    void constructor_WithAllValidParameters_ShouldCreateDTO() {
        // Act
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Assert
        assertEquals("testuser", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("password123", dto.getConfirmPassword());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    void defaultConstructor_ShouldCreateEmptyDTO() {
        // Act
        UserRegistrationDTO dto = new UserRegistrationDTO();

        // Assert
        assertNull(dto.getUsername());
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getConfirmPassword());
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
    }

    @Test
    void isValid_WithAllValidFields_ShouldReturnTrue() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertTrue(dto.isValid());
    }

    @Test
    void isValid_WithNullUsername_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                null, 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyUsername_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithWhitespaceUsername_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "   ", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithNullEmail_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                null, 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyEmail_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithNullPassword_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                null, 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyPassword_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "", 
                "password123", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithNullConfirmPassword_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                null, 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithMismatchedPasswords_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "differentPassword", 
                "John", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithNullFirstName_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                null, 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyFirstName_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                "", 
                "Doe"
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithNullLastName_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                null
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void isValid_WithEmptyLastName_ShouldReturnFalse() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                ""
        );

        // Act & Assert
        assertFalse(dto.isValid());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();

        // Act
        dto.setUsername("newuser");
        dto.setEmail("new@example.com");
        dto.setPassword("newpassword");
        dto.setConfirmPassword("newpassword");
        dto.setFirstName("Jane");
        dto.setLastName("Smith");

        // Assert
        assertEquals("newuser", dto.getUsername());
        assertEquals("new@example.com", dto.getEmail());
        assertEquals("newpassword", dto.getPassword());
        assertEquals("newpassword", dto.getConfirmPassword());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertTrue(dto.isValid());
    }
}