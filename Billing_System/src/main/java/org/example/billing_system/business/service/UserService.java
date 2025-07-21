package org.example.billing_system.business.service;

import org.example.billing_system.persistence.dao.UserDAO;
import org.example.billing_system.business.dto.UserLoginDTO;
import org.example.billing_system.business.dto.UserRegistrationDTO;
import org.example.billing_system.business.dto.UserResponseDTO;
import org.example.billing_system.business.mapper.UserMapper;
import org.example.billing_system.persistence.model.UserModel;
import org.example.billing_system.util.PasswordUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) throws SQLException {
        // Validate input
        if (!registrationDTO.isValid()) {
            throw new IllegalArgumentException("Invalid registration data");
        }

        // Check if username already exists
        if (userDAO.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Check if email already exists
        if (userDAO.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Hash password
        String hashedPassword = PasswordUtil.hashPassword(registrationDTO.getPassword());
        registrationDTO.setPassword(hashedPassword);

        // Convert DTO to Entity
        UserModel user = UserMapper.toEntity(registrationDTO);

        // Save user
        UserModel savedUser = userDAO.save(user);

        // Convert to response DTO
        return UserMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO loginUser(UserLoginDTO loginDTO) throws SQLException {
        // Validate input
        if (!loginDTO.isValid()) {
            throw new IllegalArgumentException("Invalid login credentials");
        }

        // Find user by username
        Optional<UserModel> userOptional = userDAO.findByUsername(loginDTO.getUsername());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        UserModel user = userOptional.get();

        // Verify password
        if (!PasswordUtil.verifyPassword(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Convert to response DTO
        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO getUserById(Long id) throws SQLException {
        Optional<UserModel> userOptional = userDAO.findById(id);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        return UserMapper.toResponseDTO(userOptional.get());
    }

    public UserResponseDTO getUserByUsername(String username) throws SQLException {
        Optional<UserModel> userOptional = userDAO.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        return UserMapper.toResponseDTO(userOptional.get());
    }

    public List<UserResponseDTO> getAllUsers() throws SQLException {
        List<UserModel> users = userDAO.findAll();
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        return !userDAO.existsByUsername(username);
    }

    public boolean isEmailAvailable(String email) throws SQLException {
        return !userDAO.existsByEmail(email);
    }
}
