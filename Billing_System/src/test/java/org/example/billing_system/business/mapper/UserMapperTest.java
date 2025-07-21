package org.example.billing_system.business.mapper;

import org.example.billing_system.business.dto.UserRegistrationDTO;
import org.example.billing_system.business.dto.UserResponseDTO;
import org.example.billing_system.persistence.model.UserModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toEntity_WithValidDTO_ShouldReturnUserModel() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "hashedPassword", 
                "hashedPassword", 
                "John", 
                "Doe"
        );

        // Act
        UserModel result = UserMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertNull(result.getId()); // ID should not be set from DTO
    }

    @Test
    void toEntity_WithNullDTO_ShouldReturnNull() {
        // Act
        UserModel result = UserMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponseDTO_WithValidUserModel_ShouldReturnUserResponseDTO() {
        // Arrange
        UserModel user = new UserModel("testuser", "test@example.com", "hashedPassword", "John", "Doe");
        user.setId(1);

        // Act
        UserResponseDTO result = UserMapper.toResponseDTO(user);

        // Assert
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void toResponseDTO_WithNullUserModel_ShouldReturnNull() {
        // Act
        UserResponseDTO result = UserMapper.toResponseDTO(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponseDTO_WithUserModelWithoutId_ShouldHandleNullId() {
        // Arrange
        UserModel user = new UserModel("testuser", "test@example.com", "hashedPassword", "John", "Doe");
        // ID is null by default

        // Act & Assert
        assertThrows(NullPointerException.class, () -> UserMapper.toResponseDTO(user));
    }

    @Test
    void updateEntityFromDTO_WithValidInputs_ShouldUpdateUserModel() {
        // Arrange
        UserModel existingUser = new UserModel("olduser", "old@example.com", "oldPassword", "Old", "User");
        existingUser.setId(1);

        UserRegistrationDTO dto = new UserRegistrationDTO(
                "newuser", 
                "new@example.com", 
                "newPassword", 
                "newPassword", 
                "New", 
                "User"
        );

        // Act
        UserModel result = UserMapper.updateEntityFromDTO(existingUser, dto);

        // Assert
        assertNotNull(result);
        assertSame(existingUser, result); // Should return the same instance
        assertEquals(Integer.valueOf(1), result.getId()); // ID should remain unchanged
        assertEquals("newuser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("oldPassword", result.getPassword()); // Password should not be updated
        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());
    }

    @Test
    void updateEntityFromDTO_WithNullExistingUser_ShouldReturnNull() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password", 
                "password", 
                "John", 
                "Doe"
        );

        // Act
        UserModel result = UserMapper.updateEntityFromDTO(null, dto);

        // Assert
        assertNull(result);
    }

    @Test
    void updateEntityFromDTO_WithNullDTO_ShouldReturnExistingUser() {
        // Arrange
        UserModel existingUser = new UserModel("testuser", "test@example.com", "password", "John", "Doe");
        existingUser.setId(1);

        // Act
        UserModel result = UserMapper.updateEntityFromDTO(existingUser, null);

        // Assert
        assertSame(existingUser, result);
        // Values should remain unchanged
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void updateEntityFromDTO_WithBothNull_ShouldReturnNull() {
        // Act
        UserModel result = UserMapper.updateEntityFromDTO(null, null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_WithEmptyStringsInDTO_ShouldCreateUserModelWithEmptyStrings() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO("", "", "", "", "", "");

        // Act
        UserModel result = UserMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getUsername());
        assertEquals("", result.getEmail());
        assertEquals("", result.getPassword());
        assertEquals("", result.getFirstName());
        assertEquals("", result.getLastName());
    }

    @Test
    void toResponseDTO_WithZeroId_ShouldReturnDTOWithZeroId() {
        // Arrange
        UserModel user = new UserModel("testuser", "test@example.com", "password", "John", "Doe");
        user.setId(0);

        // Act
        UserResponseDTO result = UserMapper.toResponseDTO(user);

        // Assert
        assertNotNull(result);
        assertEquals(Integer.valueOf(0), result.getId());
    }

    @Test
    void toResponseDTO_WithNegativeId_ShouldReturnDTOWithNegativeId() {
        // Arrange
        UserModel user = new UserModel("testuser", "test@example.com", "password", "John", "Doe");
        user.setId(-1);

        // Act
        UserResponseDTO result = UserMapper.toResponseDTO(user);

        // Assert
        assertNotNull(result);
        assertEquals(Integer.valueOf(-1), result.getId());
    }
}