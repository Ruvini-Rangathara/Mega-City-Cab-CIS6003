package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.exception.MegaCityCabException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userService = new UserServiceImpl();
        connection = DBUtil.getConnection();
        // Clean the users table before each test
        connection.prepareStatement("DELETE FROM users").executeUpdate();
    }

    // Helper method to add a user and return its ID
    private String addUserAndGetId(UserDTO user) throws MegaCityCabException, SQLException, ClassNotFoundException {
        userService.add(user);
        UserDTO addedUser = userService.searchByEmail(user.getEmail());
        if (addedUser == null || addedUser.getId() == null) {
            throw new RuntimeException("Failed to retrieve added user ID");
        }
        return addedUser.getId();
    }

    @Test
    void add() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Test successful addition
        UserDTO user = new UserDTO.UserDTOBuilder()
                .name("Test Add User")
                .email("testadd@example.com")
                .password("password123")
                .role(Role.user)
                .build();

        boolean result = userService.add(user);
        assertTrue(result, "User should be added successfully");

        // Verify user exists
        UserDTO addedUser = userService.searchByEmail("testadd@example.com");
        assertNotNull(addedUser, "Added user should exist");
        assertEquals("Test Add User", addedUser.getName(), "Name should match");
        assertEquals("testadd@example.com", addedUser.getEmail(), "Email should match");
        assertNotEquals("password123", addedUser.getPassword(), "Password should be hashed");
    }

    @Test
    void update() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a user first
        UserDTO user = new UserDTO.UserDTOBuilder()
                .name("Test Update User")
                .email("testupdate@example.com")
                .password("password123")
                .role(Role.user)
                .createdAt(new Date())
                .build();
        String userId = addUserAndGetId(user);

        // Test successful update
        UserDTO updatedUser = new UserDTO.UserDTOBuilder()
                .id(userId)
                .name("Updated User")
                .email("testupdate@example.com") // Email must match original
                .password("newpassword123")
                .role(Role.admin)
                .build();

        boolean result = userService.update(updatedUser);
        assertTrue(result, "User should be updated successfully");

        UserDTO fetchedUser = userService.searchById(userId);
        assertEquals("Updated User", fetchedUser.getName(), "Name should be updated");
        assertEquals(Role.admin, fetchedUser.getRole(), "Role should be updated");
        assertNotEquals("newpassword123", fetchedUser.getPassword(), "Password should be hashed differently");
    }

    @Test
    void delete() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a user first
        UserDTO user = new UserDTO.UserDTOBuilder()
                .name("Test Delete User")
                .email("testdelete@example.com")
                .password("password123")
                .role(Role.user)
                .createdAt(new Date())
                .build();
        String userId = addUserAndGetId(user);

        // Test successful deletion
        boolean result = userService.delete(userId);
        assertTrue(result, "User should be deleted successfully");

    }

    @Test
    void searchById() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a user first
        UserDTO user = new UserDTO.UserDTOBuilder()
                .name("Test Id User")
                .email("testid@example.com")
                .password("password123")
                .role(Role.user)
                .build();
        String userId = addUserAndGetId(user);

        // Test successful search
        UserDTO foundUser = userService.searchById(userId);
        assertNotNull(foundUser, "User should be found by ID");
        assertEquals("Test Id User", foundUser.getName(), "Name should match");
        assertEquals("testid@example.com", foundUser.getEmail(), "Email should match");
    }

    @Test
    void getAll() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add multiple users
        UserDTO user1 = new UserDTO.UserDTOBuilder()
                .name("User One")
                .email("user1@example.com")
                .password("password123")
                .role(Role.user)
                .build();
        UserDTO user2 = new UserDTO.UserDTOBuilder()
                .name("User Two")
                .email("user2@example.com")
                .password("password456")
                .role(Role.admin)
                .build();
        userService.add(user1);
        userService.add(user2);

        // Test getAll without search params
        List<UserDTO> allUsers = userService.getAll(null);
        assertEquals(2, allUsers.size(), "Should return all users");
    }

    @Test
    void existByPk() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a user first
        UserDTO user = new UserDTO.UserDTOBuilder()
                .name("Test Exist User")
                .email("testexist@example.com")
                .password("password123")
                .role(Role.user)
                .build();
        String userId = addUserAndGetId(user);

        // Test existing user
        boolean exists = userService.existByPk(userId);
        assertTrue(exists, "User should exist");

        // Test non-existent user
        boolean notExists = userService.existByPk("U999");
        assertFalse(notExists, "Non-existent user should not exist");
    }

    @Test
    void searchByEmail() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a user first
        UserDTO user = new UserDTO.UserDTOBuilder()
                .name("Test Search User")
                .email("testsearch@example.com")
                .password("password123")
                .role(Role.user)
                .build();
        userService.add(user);

        // Test successful search by email
        UserDTO foundUser = userService.searchByEmail("testsearch@example.com");
        assertNotNull(foundUser, "User should be found by email");
        assertEquals("Test Search User", foundUser.getName(), "Name should match");

        // Test non-existent email
        UserDTO notFoundUser = userService.searchByEmail("nonexistent@example.com");
        assertNull(notFoundUser, "Non-existent email should return null");
    }
}
