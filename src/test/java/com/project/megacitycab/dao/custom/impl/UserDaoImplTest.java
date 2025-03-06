package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.entity.User;
import com.project.megacitycab.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDaoImpl userDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        userDao = new UserDaoImpl();
        connection = DBUtil.getConnection();
        // Ensure the database is in a clean state before each test
        connection.prepareStatement("DELETE FROM users").executeUpdate();
    }

    @Test
    void add() throws SQLException {
        User user = new User.UserBuilder()
                .name("John Doe Test")
                .email("johntest@example.com")
                .password("hashedPassword")
                .salt("randomSalt")
                .role(Role.user)
                .build();

        boolean result = userDao.add(connection, user);
        assertTrue(result, "User should be added successfully");

        // Verify the user was actually added
        User savedUser = userDao.findByEmail(connection, "johntest@example.com");
        assertNotNull(savedUser, "User should not be null");
        assertEquals("John Doe Test", savedUser.getName(), "Name should match");
    }

    @Test
    void update() throws SQLException {
        // First add a user
        User user = new User.UserBuilder()
                .name("Jane Doe Test")
                .email("jane@example.com")
                .password("oldHash")
                .salt("salt")
                .role(Role.user)
                .build();
        userDao.add(connection, user);

        // Retrieve the added user to get the ID
        User addedUser = userDao.findByEmail(connection, "jane@example.com");
        assertNotNull(addedUser, "User should exist after being added");

        // Update the user
        User updatedUser = new User.UserBuilder()
                .id(addedUser.getId())
                .name("Jane Smith")
                .email("jane@example.com")
                .password("newHash")
                .salt("salt")
                .role(Role.admin)
                .build();

        boolean result = userDao.update(connection, updatedUser);
        assertTrue(result, "User should be updated successfully");

        // Verify update
        User fetchedUser = userDao.searchById(connection, addedUser.getId());
        assertNotNull(fetchedUser, "User should not be null");
        assertEquals("Jane Smith", fetchedUser.getName(), "Name should be updated");
        assertEquals(Role.admin, fetchedUser.getRole(), "Role should be updated");
    }

    @Test
    void delete() throws SQLException {
        // Add a user first
        User user = new User.UserBuilder()
                .name("Delete Me")
                .email("delete@example.com")
                .password("hash")
                .salt("salt")
                .role(Role.user)
                .build();
        userDao.add(connection, user);

        // Retrieve the added user to get the ID
        User addedUser = userDao.findByEmail(connection, "delete@example.com");
        assertNotNull(addedUser, "User should exist after being added");

        boolean result = userDao.delete(connection, addedUser.getId());
        assertTrue(result, "User should be soft-deleted successfully");

        // Verify soft delete
        User deletedUser = userDao.searchById(connection, addedUser.getId());
        assertNotNull(deletedUser.getDeletedAt(), "DeletedAt should not be null");
    }

    @Test
    void searchById() throws SQLException {
        User user = new User.UserBuilder()
                .name("Search Me")
                .email("search@example.com")
                .password("hash")
                .salt("salt")
                .role(Role.user)
                .build();
        userDao.add(connection, user);

        // Retrieve the added user to get the ID
        User addedUser = userDao.findByEmail(connection, "search@example.com");
        assertNotNull(addedUser, "User should exist after being added");

        User foundUser = userDao.searchById(connection, addedUser.getId());
        assertNotNull(foundUser, "User should not be null");
        assertEquals("Search Me", foundUser.getName(), "Name should match");
        assertEquals("search@example.com", foundUser.getEmail(), "Email should match");
    }

    @Test
    void getAll() throws SQLException {
        // Add some test users
        User user1 = new User.UserBuilder()
                .name("User1")
                .email("user1@example.com")
                .password("hash1")
                .salt("salt1")
                .role(Role.user)
                .build();
        User user2 = new User.UserBuilder()
                .name("User2")
                .email("user2@example.com")
                .password("hash2")
                .salt("salt2")
                .role(Role.user)
                .build();
        userDao.add(connection, user1);
        userDao.add(connection, user2);

        List<User> users = userDao.getAll(connection, null);
        assertTrue(users.size() >= 2, "At least 2 users should exist");
    }

    @Test
    void existByPk() throws SQLException {
        User user = new User.UserBuilder()
                .name("Exist Test")
                .email("exist@example.com")
                .password("hash")
                .salt("salt")
                .role(Role.user)
                .build();
        userDao.add(connection, user);

        // Retrieve the added user to get the ID
        User addedUser = userDao.findByEmail(connection, "exist@example.com");
        assertNotNull(addedUser, "User should exist after being added");

        boolean exists = userDao.existByPk(connection, addedUser.getId());
        assertTrue(exists, "User should exist with given PK");

        boolean notExists = userDao.existByPk(connection, "non-existent-id");
        assertFalse(notExists, "User should not exist with non-existent ID");
    }

    @Test
    void existByEmail() throws SQLException {
        User user = new User.UserBuilder()
                .name("Email Test")
                .email("emailtest@example.com")
                .password("hash")
                .salt("salt")
                .role(Role.user)
                .build();
        userDao.add(connection, user);

        boolean exists = userDao.existByEmail(connection, "emailtest@example.com");
        assertTrue(exists, "User should exist with given email");

        boolean notExists = userDao.existByEmail(connection, "nonexistent@example.com");
        assertFalse(notExists, "User should not exist with non-existent email");
    }

    @Test
    void findByEmail() throws SQLException {
        User user = new User.UserBuilder()
                .name("Find Me")
                .email("findme@example.com")
                .password("hash")
                .salt("salt")
                .role(Role.user)
                .build();
        userDao.add(connection, user);

        User foundUser = userDao.findByEmail(connection, "findme@example.com");
        assertNotNull(foundUser, "User should not be null");
        assertEquals("Find Me", foundUser.getName(), "Name should match");

        User notFound = userDao.findByEmail(connection, "notfound@example.com");
        assertNull(notFound, "User should not exist with non-existent email");
    }
}
