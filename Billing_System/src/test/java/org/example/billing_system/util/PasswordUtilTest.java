package org.example.billing_system.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void hashPassword_ShouldReturnHashedPassword() {
        // Arrange
        String password = "testPassword123";

        // Act
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Assert
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
        assertTrue(hashedPassword.length() > 0);
    }

    @Test
    void hashPassword_WithSamePassword_ShouldReturnDifferentHashes() {
        // Arrange
        String password = "testPassword123";

        // Act
        String hash1 = PasswordUtil.hashPassword(password);
        String hash2 = PasswordUtil.hashPassword(password);

        // Assert
        assertNotEquals(hash1, hash2, "Same password should produce different hashes due to salt");
    }

    @Test
    void verifyPassword_WithCorrectPassword_ShouldReturnTrue() {
        // Arrange
        String password = "testPassword123";
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Act
        boolean result = PasswordUtil.verifyPassword(password, hashedPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyPassword_WithIncorrectPassword_ShouldReturnFalse() {
        // Arrange
        String password = "testPassword123";
        String wrongPassword = "wrongPassword";
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Act
        boolean result = PasswordUtil.verifyPassword(wrongPassword, hashedPassword);

        // Assert
        assertFalse(result);
    }

    @Test
    void verifyPassword_WithNullPassword_ShouldReturnFalse() {
        // Arrange
        String hashedPassword = PasswordUtil.hashPassword("testPassword123");

        // Act
        boolean result = PasswordUtil.verifyPassword(null, hashedPassword);

        // Assert
        assertFalse(result);
    }

    @Test
    void verifyPassword_WithNullHash_ShouldReturnFalse() {
        // Act
        boolean result = PasswordUtil.verifyPassword("testPassword123", null);

        // Assert
        assertFalse(result);
    }

    @Test
    void verifyPassword_WithEmptyPassword_ShouldReturnFalse() {
        // Arrange
        String hashedPassword = PasswordUtil.hashPassword("testPassword123");

        // Act
        boolean result = PasswordUtil.verifyPassword("", hashedPassword);

        // Assert
        assertFalse(result);
    }

    @Test
    void verifyPassword_WithEmptyHash_ShouldReturnFalse() {
        // Act
        boolean result = PasswordUtil.verifyPassword("testPassword123", "");

        // Assert
        assertFalse(result);
    }

    @Test
    void verifyPassword_WithInvalidHash_ShouldReturnFalse() {
        // Act
        boolean result = PasswordUtil.verifyPassword("testPassword123", "invalidHash");

        // Assert
        assertFalse(result);
    }

    @Test
    void hashPassword_WithEmptyPassword_ShouldReturnHash() {
        // Act
        String hashedPassword = PasswordUtil.hashPassword("");

        // Assert
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);
    }

    @Test
    void hashPassword_WithSpecialCharacters_ShouldReturnHash() {
        // Arrange
        String password = "!@#$%^&*()_+-=[]{}|;:,.<>?";

        // Act
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Assert
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);
        assertTrue(PasswordUtil.verifyPassword(password, hashedPassword));
    }

    @Test
    void hashPassword_WithUnicodeCharacters_ShouldReturnHash() {
        // Arrange
        String password = "测试密码🔒";

        // Act
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Assert
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);
        assertTrue(PasswordUtil.verifyPassword(password, hashedPassword));
    }

    @Test
    void hashPassword_WithLongPassword_ShouldReturnHash() {
        // Arrange
        String password = "a".repeat(1000);

        // Act
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Assert
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);
        assertTrue(PasswordUtil.verifyPassword(password, hashedPassword));
    }
}