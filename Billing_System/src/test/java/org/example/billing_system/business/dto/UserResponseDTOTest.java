package org.example.billing_system.business.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDTOTest {

    @Test
    void constructor_WithValidParameters_ShouldCreateDTO() {
        // Act
        UserResponseDTO dto = new UserResponseDTO(1, "testuser", "test@example.com", "John", "Doe");

        // Assert
        assertEquals(Integer.valueOf(1), dto.getId());
        assertEquals("testuser", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    void constructor_WithNullValues_ShouldCreateDTO() {
        // Act
        UserResponseDTO dto = new UserResponseDTO(null, null, null, null, null);

        // Assert
        assertNull(dto.getId());
        assertNull(dto.getUsername());
        assertNull(dto.getEmail());
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
    }

    @Test
    void setters_ShouldUpdateFields() {
        // Arrange
        UserResponseDTO dto = new UserResponseDTO(1, "testuser", "test@example.com", "John", "Doe");

        // Act
        dto.setId(2);
        dto.setUsername("newuser");
        dto.setEmail("new@example.com");
        dto.setFirstName("Jane");
        dto.setLastName("Smith");

        // Assert
        assertEquals(Integer.valueOf(2), dto.getId());
        assertEquals("newuser", dto.getUsername());
        assertEquals("new@example.com", dto.getEmail());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
    }

    @Test
    void getters_ShouldReturnCorrectValues() {
        // Arrange
        UserResponseDTO dto = new UserResponseDTO(100, "admin", "admin@example.com", "Admin", "User");

        // Act & Assert
        assertEquals(Integer.valueOf(100), dto.getId());
        assertEquals("admin", dto.getUsername());
        assertEquals("admin@example.com", dto.getEmail());
        assertEquals("Admin", dto.getFirstName());
        assertEquals("User", dto.getLastName());
    }

    @Test
    void constructor_WithZeroId_ShouldCreateDTO() {
        // Act
        UserResponseDTO dto = new UserResponseDTO(0, "testuser", "test@example.com", "John", "Doe");

        // Assert
        assertEquals(Integer.valueOf(0), dto.getId());
        assertEquals("testuser", dto.getUsername());
    }

    @Test
    void constructor_WithNegativeId_ShouldCreateDTO() {
        // Act
        UserResponseDTO dto = new UserResponseDTO(-1, "testuser", "test@example.com", "John", "Doe");

        // Assert
        assertEquals(Integer.valueOf(-1), dto.getId());
        assertEquals("testuser", dto.getUsername());
    }

    @Test
    void constructor_WithEmptyStrings_ShouldCreateDTO() {
        // Act
        UserResponseDTO dto = new UserResponseDTO(1, "", "", "", "");

        // Assert
        assertEquals(Integer.valueOf(1), dto.getId());
        assertEquals("", dto.getUsername());
        assertEquals("", dto.getEmail());
        assertEquals("", dto.getFirstName());
        assertEquals("", dto.getLastName());
    }
}