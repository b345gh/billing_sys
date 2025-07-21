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

    private AutoCloseable passwordUtilMock;
    private AutoCloseable userMapperMock;

    @BeforeEach
    void setup() {
        userService = new UserService(userDAO);
    }

    @Test
    void registerUser_ShouldRegisterSuccessfully() throws SQLException {
        UserRegistrationDTO dto = new UserRegistrationDTO("ramitha", "ramitha@gmail.com", "1234");

        // Make input valid
        assertTrue(dto.isValid());

        when(userDAO.existsByUsername("ramitha")).thenReturn(false);
        when(userDAO.existsByEmail("ramitha@gmail.com")).thenReturn(false);

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class);
             MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {

            passwordUtil.when(() -> PasswordUtil.hashPassword("1234")).thenReturn("hashed1234");

            UserModel entity = new UserModel(1L, "ramitha", "ramitha@gmail.com", "hashed1234");
            UserModel savedEntity = new UserModel(1L, "ramitha", "ramitha@gmail.com", "hashed1234");
            UserResponseDTO responseDTO = new UserResponseDTO(1L, "ramitha", "ramitha@gmail.com");

            userMapper.when(() -> UserMapper.toEntity(any())).thenReturn(entity);
            when(userDAO.save(entity)).thenReturn(savedEntity);
            userMapper.when(() -> UserMapper.toResponseDTO(savedEntity)).thenReturn(responseDTO);

            UserResponseDTO result = userService.registerUser(dto);

            assertEquals("ramitha", result.getUsername());
        }
    }

    @Test
    void registerUser_ShouldFailIfUsernameExists() throws SQLException {
        UserRegistrationDTO dto = new UserRegistrationDTO("ramitha", "ramitha@gmail.com", "1234");
        when(userDAO.existsByUsername("ramitha")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(dto));
    }

    @Test
    void loginUser_ShouldLoginSuccessfully() throws SQLException {
        UserLoginDTO dto = new UserLoginDTO("ramitha", "1234");

        UserModel user = new UserModel(1L, "ramitha", "ramitha@gmail.com", "hashed1234");
        UserResponseDTO expectedDTO = new UserResponseDTO(1L, "ramitha", "ramitha@gmail.com");

        when(userDAO.findByUsername("ramitha")).thenReturn(Optional.of(user));

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class);
             MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {

            passwordUtil.when(() -> PasswordUtil.verifyPassword("1234", "hashed1234")).thenReturn(true);
            userMapper.when(() -> UserMapper.toResponseDTO(user)).thenReturn(expectedDTO);

            UserResponseDTO result = userService.loginUser(dto);
            assertEquals("ramitha", result.getUsername());
        }
    }

    @Test
    void loginUser_ShouldFailIfPasswordInvalid() throws SQLException {
        UserLoginDTO dto = new UserLoginDTO("ramitha", "wrong");

        UserModel user = new UserModel(1L, "ramitha", "ramitha@gmail.com", "hashed1234");

        when(userDAO.findByUsername("ramitha")).thenReturn(Optional.of(user));

        try (MockedStatic<PasswordUtil> passwordUtil = mockStatic(PasswordUtil.class)) {
            passwordUtil.when(() -> PasswordUtil.verifyPassword("wrong", "hashed1234")).thenReturn(false);
            assertThrows(IllegalArgumentException.class, () -> userService.loginUser(dto));
        }
    }

    @Test
    void getUserById_ShouldReturnUser() throws SQLException {
        UserModel user = new UserModel(1L, "ramitha", "ramitha@gmail.com", "pass");
        UserResponseDTO expected = new UserResponseDTO(1L, "ramitha", "ramitha@gmail.com");

        when(userDAO.findById(1L)).thenReturn(Optional.of(user));

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.toResponseDTO(user)).thenReturn(expected);

            UserResponseDTO result = userService.getUserById(1L);
            assertEquals("ramitha", result.getUsername());
        }
    }

    @Test
    void getUserByUsername_ShouldReturnUser() throws SQLException {
        UserModel user = new UserModel(1L, "ramitha", "ramitha@gmail.com", "pass");
        UserResponseDTO expected = new UserResponseDTO(1L, "ramitha", "ramitha@gmail.com");

        when(userDAO.findByUsername("ramitha")).thenReturn(Optional.of(user));

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.toResponseDTO(user)).thenReturn(expected);

            UserResponseDTO result = userService.getUserByUsername("ramitha");
            assertEquals("ramitha", result.getUsername());
        }
    }

    @Test
    void getAllUsers_ShouldReturnUserList() throws SQLException {
        List<UserModel> users = List.of(
                new UserModel(1L, "ramitha", "a@gmail.com", "pass"),
                new UserModel(2L, "heshan", "b@gmail.com", "pass")
        );

        when(userDAO.findAll()).thenReturn(users);

        try (MockedStatic<UserMapper> userMapper = mockStatic(UserMapper.class)) {
            userMapper.when(() -> UserMapper.toResponseDTO(users.get(0)))
                    .thenReturn(new UserResponseDTO(1L, "ramitha", "a@gmail.com"));
            userMapper.when(() -> UserMapper.toResponseDTO(users.get(1)))
                    .thenReturn(new UserResponseDTO(2L, "heshan", "b@gmail.com"));

            List<UserResponseDTO> result = userService.getAllUsers();
            assertEquals(2, result.size());
        }
    }

    @Test
    void isUsernameAvailable_ShouldReturnTrue_WhenAvailable() throws SQLException {
        when(userDAO.existsByUsername("newuser")).thenReturn(false);
        assertTrue(userService.isUsernameAvailable("newuser"));
    }

    @Test
    void isEmailAvailable_ShouldReturnFalse_WhenExists() throws SQLException {
        when(userDAO.existsByEmail("email@gmail.com")).thenReturn(true);
        assertFalse(userService.isEmailAvailable("email@gmail.com"));
    }
}
