package org.example.billing_system.business.service;

import org.example.billing_system.business.dto.*;
import org.example.billing_system.business.mapper.UserMapper;
import org.example.billing_system.persistence.dao.UserDAO;
import org.example.billing_system.persistence.model.UserModel;
import org.example.billing_system.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDTO validRegistrationDTO;
    private UserLoginDTO validLoginDTO;
    private UserModel testUser;
    private UserResponseDTO testUserResponse;

    @BeforeEach
    void setUp() {
        // Initialize test data
        validRegistrationDTO = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "password123", 
                "John", 
                "Doe"
        );

        validLoginDTO = new UserLoginDTO("testuser", "password123");

        testUser = new UserModel("testuser", "test@example.com", "hashedPassword", "John", "Doe");
        testUser.setId(1);

        testUserResponse = new UserResponseDTO(1, "testuser", "test@example.com", "John", "Doe");
    }

    // Registration Tests
    @Test
    void registerUser_WithValidData_ShouldRegisterSuccessfully() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("testuser")).thenReturn(false);
        when(userDAO.existsByEmail("test@example.com")).thenReturn(false);

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class);
             MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {

            passwordUtil.when(() -> PasswordUtil.hashPassword("password123")).thenReturn("hashedPassword");
            userMapper.when(() -> UserMapper.toEntity(any(UserRegistrationDTO.class))).thenReturn(testUser);
            when(userDAO.save(testUser)).thenReturn(testUser);
            userMapper.when(() -> UserMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

            // Act
            UserResponseDTO result = userService.registerUser(validRegistrationDTO);

            // Assert
            assertNotNull(result);
            assertEquals("testuser", result.getUsername());
            assertEquals("test@example.com", result.getEmail());
            assertEquals("John", result.getFirstName());
            assertEquals("Doe", result.getLastName());

            verify(userDAO).existsByUsername("testuser");
            verify(userDAO).existsByEmail("test@example.com");
            verify(userDAO).save(testUser);
        }
    }

    @Test
    void registerUser_WithInvalidData_ShouldThrowException() {
        // Arrange
        UserRegistrationDTO invalidDTO = new UserRegistrationDTO("", "", "", "", "", "");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(invalidDTO)
        );
        assertEquals("Invalid registration data", exception.getMessage());
    }

    @Test
    void registerUser_WithExistingUsername_ShouldThrowException() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(validRegistrationDTO)
        );
        assertEquals("Username already exists", exception.getMessage());
        verify(userDAO).existsByUsername("testuser");
        verify(userDAO, never()).existsByEmail(anyString());
    }

    @Test
    void registerUser_WithExistingEmail_ShouldThrowException() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("testuser")).thenReturn(false);
        when(userDAO.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(validRegistrationDTO)
        );
        assertEquals("Email already exists", exception.getMessage());
        verify(userDAO).existsByUsername("testuser");
        verify(userDAO).existsByEmail("test@example.com");
    }

    @Test
    void registerUser_WithPasswordMismatch_ShouldThrowException() {
        // Arrange
        UserRegistrationDTO mismatchDTO = new UserRegistrationDTO(
                "testuser", 
                "test@example.com", 
                "password123", 
                "differentPassword", 
                "John", 
                "Doe"
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(mismatchDTO)
        );
        assertEquals("Invalid registration data", exception.getMessage());
    }

    @Test
    void registerUser_WithDatabaseError_ShouldThrowSQLException() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("testuser")).thenReturn(false);
        when(userDAO.existsByEmail("test@example.com")).thenReturn(false);

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class);
             MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {

            passwordUtil.when(() -> PasswordUtil.hashPassword("password123")).thenReturn("hashedPassword");
            userMapper.when(() -> UserMapper.toEntity(any(UserRegistrationDTO.class))).thenReturn(testUser);
            when(userDAO.save(testUser)).thenThrow(new SQLException("Database connection failed"));

            // Act & Assert
            assertThrows(SQLException.class, () -> userService.registerUser(validRegistrationDTO));
        }
    }

    // Login Tests
    @Test
    void loginUser_WithValidCredentials_ShouldLoginSuccessfully() throws SQLException {
        // Arrange
        when(userDAO.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class);
             MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {

            passwordUtil.when(() -> PasswordUtil.verifyPassword("password123", "hashedPassword")).thenReturn(true);
            userMapper.when(() -> UserMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

            // Act
            UserResponseDTO result = userService.loginUser(validLoginDTO);

            // Assert
            assertNotNull(result);
            assertEquals("testuser", result.getUsername());
            verify(userDAO).findByUsername("testuser");
        }
    }

    @Test
    void loginUser_WithInvalidCredentials_ShouldThrowException() {
        // Arrange
        UserLoginDTO invalidDTO = new UserLoginDTO("", "");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser(invalidDTO)
        );
        assertEquals("Invalid login credentials", exception.getMessage());
    }

    @Test
    void loginUser_WithNonExistentUser_ShouldThrowException() throws SQLException {
        // Arrange
        when(userDAO.findByUsername("nonexistent")).thenReturn(Optional.empty());

        UserLoginDTO nonExistentUserDTO = new UserLoginDTO("nonexistent", "password123");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser(nonExistentUserDTO)
        );
        assertEquals("Invalid username or password", exception.getMessage());
        verify(userDAO).findByUsername("nonexistent");
    }

    @Test
    void loginUser_WithWrongPassword_ShouldThrowException() throws SQLException {
        // Arrange
        when(userDAO.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.verifyPassword("wrongPassword", "hashedPassword")).thenReturn(false);

            UserLoginDTO wrongPasswordDTO = new UserLoginDTO("testuser", "wrongPassword");

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> userService.loginUser(wrongPasswordDTO)
            );
            assertEquals("Invalid username or password", exception.getMessage());
        }
    }

    @Test
    void loginUser_WithDatabaseError_ShouldThrowSQLException() throws SQLException {
        // Arrange
        when(userDAO.findByUsername("testuser")).thenThrow(new SQLException("Database connection failed"));

        // Act & Assert
        assertThrows(SQLException.class, () -> userService.loginUser(validLoginDTO));
    }

    // Get User Tests
    @Test
    void getUserById_WithValidId_ShouldReturnUser() throws SQLException {
        // Arrange
        when(userDAO.findById(1L)).thenReturn(Optional.of(testUser));

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

            // Act
            UserResponseDTO result = userService.getUserById(1L);

            // Assert
            assertNotNull(result);
            assertEquals("testuser", result.getUsername());
            verify(userDAO).findById(1L);
        }
    }

    @Test
    void getUserById_WithNonExistentId_ShouldThrowException() throws SQLException {
        // Arrange
        when(userDAO.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserById(999L)
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getUserByUsername_WithValidUsername_ShouldReturnUser() throws SQLException {
        // Arrange
        when(userDAO.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

            // Act
            UserResponseDTO result = userService.getUserByUsername("testuser");

            // Assert
            assertNotNull(result);
            assertEquals("testuser", result.getUsername());
            verify(userDAO).findByUsername("testuser");
        }
    }

    @Test
    void getUserByUsername_WithNonExistentUsername_ShouldThrowException() throws SQLException {
        // Arrange
        when(userDAO.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserByUsername("nonexistent")
        );
        assertEquals("User not found", exception.getMessage());
    }

    // Get All Users Tests
    @Test
    void getAllUsers_ShouldReturnUserList() throws SQLException {
        // Arrange
        UserModel user2 = new UserModel("user2", "user2@example.com", "hashedPassword2", "Jane", "Smith");
        user2.setId(2);
        UserResponseDTO user2Response = new UserResponseDTO(2, "user2", "user2@example.com", "Jane", "Smith");

        List<UserModel> users = Arrays.asList(testUser, user2);
        when(userDAO.findAll()).thenReturn(users);

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);
            userMapper.when(() -> UserMapper.toResponseDTO(user2)).thenReturn(user2Response);

            // Act
            List<UserResponseDTO> result = userService.getAllUsers();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("testuser", result.get(0).getUsername());
            assertEquals("user2", result.get(1).getUsername());
            verify(userDAO).findAll();
        }
    }

    @Test
    void getAllUsers_WithEmptyDatabase_ShouldReturnEmptyList() throws SQLException {
        // Arrange
        when(userDAO.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userDAO).findAll();
    }

    @Test
    void getAllUsers_WithDatabaseError_ShouldThrowSQLException() throws SQLException {
        // Arrange
        when(userDAO.findAll()).thenThrow(new SQLException("Database connection failed"));

        // Act & Assert
        assertThrows(SQLException.class, () -> userService.getAllUsers());
    }

    // Username Availability Tests
    @Test
    void isUsernameAvailable_WithAvailableUsername_ShouldReturnTrue() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("newuser")).thenReturn(false);

        // Act
        boolean result = userService.isUsernameAvailable("newuser");

        // Assert
        assertTrue(result);
        verify(userDAO).existsByUsername("newuser");
    }

    @Test
    void isUsernameAvailable_WithTakenUsername_ShouldReturnFalse() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("testuser")).thenReturn(true);

        // Act
        boolean result = userService.isUsernameAvailable("testuser");

        // Assert
        assertFalse(result);
        verify(userDAO).existsByUsername("testuser");
    }

    @Test
    void isUsernameAvailable_WithDatabaseError_ShouldThrowSQLException() throws SQLException {
        // Arrange
        when(userDAO.existsByUsername("testuser")).thenThrow(new SQLException("Database connection failed"));

        // Act & Assert
        assertThrows(SQLException.class, () -> userService.isUsernameAvailable("testuser"));
    }

    // Email Availability Tests
    @Test
    void isEmailAvailable_WithAvailableEmail_ShouldReturnTrue() throws SQLException {
        // Arrange
        when(userDAO.existsByEmail("new@example.com")).thenReturn(false);

        // Act
        boolean result = userService.isEmailAvailable("new@example.com");

        // Assert
        assertTrue(result);
        verify(userDAO).existsByEmail("new@example.com");
    }

    @Test
    void isEmailAvailable_WithTakenEmail_ShouldReturnFalse() throws SQLException {
        // Arrange
        when(userDAO.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        boolean result = userService.isEmailAvailable("test@example.com");

        // Assert
        assertFalse(result);
        verify(userDAO).existsByEmail("test@example.com");
    }

    @Test
    void isEmailAvailable_WithDatabaseError_ShouldThrowSQLException() throws SQLException {
        // Arrange
        when(userDAO.existsByEmail("test@example.com")).thenThrow(new SQLException("Database connection failed"));

        // Act & Assert
        assertThrows(SQLException.class, () -> userService.isEmailAvailable("test@example.com"));
    }

    // Edge Cases and Null Tests
    @Test
    void registerUser_WithNullDTO_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.registerUser(null));
    }

    @Test
    void loginUser_WithNullDTO_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.loginUser(null));
    }

    @Test
    void getUserById_WithNullId_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.getUserById(null));
    }

    @Test
    void getUserByUsername_WithNullUsername_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.getUserByUsername(null));
    }

    @Test
    void isUsernameAvailable_WithNullUsername_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.isUsernameAvailable(null));
    }

    @Test
    void isEmailAvailable_WithNullEmail_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> userService.isEmailAvailable(null));
    }
}