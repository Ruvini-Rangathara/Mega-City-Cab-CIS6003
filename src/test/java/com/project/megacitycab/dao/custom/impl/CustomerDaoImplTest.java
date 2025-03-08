package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.entity.Customer;
import com.project.megacitycab.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoImplTest {

    private CustomerDaoImpl customerDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        customerDao = new CustomerDaoImpl();
        connection = DBUtil.getConnection();
        // Ensure the database is in a clean state before each test
        connection.prepareStatement("DELETE FROM customers").executeUpdate();
    }

    // Helper method to find customer by registrationNo
    private Customer findCustomerByRegNo(String regNo) throws SQLException, ClassNotFoundException {
        List<Customer> customers = customerDao.getAll(connection, null);
        return customers.stream()
                .filter(c -> c.getRegistrationNo().equals(regNo))
                .findFirst()
                .orElse(null);
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG123")
                .name("John Doe")
                .address("123 Main St")
                .nic("NIC123456")
                .dob(new Date())
                .mobileNo("1234567890")
                .email("john@example.com")
                .build();

        boolean result = customerDao.add(connection, customer);
        assertTrue(result, "Customer should be added successfully");

        // Verify the customer was actually added
        Customer addedCustomer = findCustomerByRegNo("REG123");
        assertNotNull(addedCustomer, "Customer should not be null");
        assertEquals("John Doe", addedCustomer.getName(), "Name should match");
    }

    @Test
    void update() throws SQLException, ClassNotFoundException {
        // First add a customer
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG456")
                .name("Jane Doe")
                .address("456 Oak St")
                .nic("NIC654321")
                .dob(new Date())
                .mobileNo("0987654321")
                .email("jane@example.com")
                .build();
        customerDao.add(connection, customer);

        // Retrieve the added customer to get the ID
        Customer addedCustomer = findCustomerByRegNo("REG456");
        assertNotNull(addedCustomer, "Customer should exist after being added");

        // Update the customer
        Customer updatedCustomer = new Customer.CustomerBuilder()
                .id(addedCustomer.getId())
                .registrationNo("REG456")
                .name("Jane Smith")
                .address("789 Pine St")
                .nic("NIC654321")
                .dob(new Date())
                .mobileNo("1112223333")
                .email("jane.smith@example.com")
                .build();

        boolean result = customerDao.update(connection, updatedCustomer);
        assertTrue(result, "Customer should be updated successfully");

        // Verify update
        Customer fetchedCustomer = customerDao.searchById(connection, addedCustomer.getId());
        assertNotNull(fetchedCustomer, "Customer should not be null");
        assertEquals("Jane Smith", fetchedCustomer.getName(), "Name should be updated");
        assertEquals("789 Pine St", fetchedCustomer.getAddress(), "Address should be updated");
    }

    @Test
    void delete() throws SQLException, ClassNotFoundException {
        // Add a customer first
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG789")
                .name("Delete Me")
                .address("999 Delete St")
                .nic("NIC999999")
                .dob(new Date())
                .mobileNo("9999999999")
                .email("delete@example.com")
                .build();
        customerDao.add(connection, customer);

        // Retrieve the added customer to get the ID
        Customer addedCustomer = findCustomerByRegNo("REG789");
        assertNotNull(addedCustomer, "Customer should exist after being added");

        boolean result = customerDao.delete(connection, addedCustomer.getId());
        assertTrue(result, "Customer should be soft-deleted successfully");

        // Verify soft delete
        Customer deletedCustomer = customerDao.searchById(connection, addedCustomer.getId());
        assertNull(deletedCustomer, "Customer should not be returned after soft delete");
    }

    @Test
    void searchById() throws SQLException, ClassNotFoundException {
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG101")
                .name("Search Me")
                .address("101 Search St")
                .nic("NIC101010")
                .dob(new Date())
                .mobileNo("1010101010")
                .email("search@example.com")
                .build();
        customerDao.add(connection, customer);

        // Retrieve the added customer to get the ID
        Customer addedCustomer = findCustomerByRegNo("REG101");
        assertNotNull(addedCustomer, "Customer should exist after being added");

        Customer foundCustomer = customerDao.searchById(connection, addedCustomer.getId());
        assertNotNull(foundCustomer, "Customer should not be null");
        assertEquals("Search Me", foundCustomer.getName(), "Name should match");
        assertEquals("search@example.com", foundCustomer.getEmail(), "Email should match");
    }

    @Test
    void getAll() throws SQLException, ClassNotFoundException {
        // Add some test customers
        Customer customer1 = new Customer.CustomerBuilder()
                .registrationNo("REG111")
                .name("Customer1")
                .address("111 Test St")
                .nic("NIC111111")
                .dob(new Date())
                .mobileNo("1111111111")
                .email("customer1@example.com")
                .build();
        Customer customer2 = new Customer.CustomerBuilder()
                .registrationNo("REG222")
                .name("Customer2")
                .address("222 Test St")
                .nic("NIC222222")
                .dob(new Date())
                .mobileNo("2222222222")
                .email("customer2@example.com")
                .build();
        customerDao.add(connection, customer1);
        customerDao.add(connection, customer2);

        List<Customer> customers = customerDao.getAll(connection, null);
        assertTrue(customers.size() >= 2, "At least 2 customers should exist");
    }

    @Test
    void existByPk() throws SQLException, ClassNotFoundException {
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG333")
                .name("Exist Test")
                .address("333 Exist St")
                .nic("NIC333333")
                .dob(new Date())
                .mobileNo("3333333333")
                .email("exist@example.com")
                .build();
        customerDao.add(connection, customer);

        // Retrieve the added customer to get the ID
        Customer addedCustomer = findCustomerByRegNo("REG333");
        assertNotNull(addedCustomer, "Customer should exist after being added");

        boolean exists = customerDao.existByPk(connection, addedCustomer.getId());
        assertTrue(exists, "Customer should exist with given PK");

        boolean notExists = customerDao.existByPk(connection, "non-existent-id");
        assertFalse(notExists, "Customer should not exist with non-existent ID");
    }

    @Test
    void existByEmail() throws SQLException, ClassNotFoundException {
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG444")
                .name("Email Test")
                .address("444 Email St")
                .nic("NIC444444")
                .dob(new Date())
                .mobileNo("4444444444")
                .email("emailtest@example.com")
                .build();
        customerDao.add(connection, customer);

        boolean exists = customerDao.existByEmail(connection, "emailtest@example.com");
        assertTrue(exists, "Customer should exist with given email");

        boolean notExists = customerDao.existByEmail(connection, "nonexistent@example.com");
        assertFalse(notExists, "Customer should not exist with non-existent email");
    }

    @Test
    void existByRegNo() throws SQLException, ClassNotFoundException {
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG555")
                .name("RegNo Test")
                .address("555 Reg St")
                .nic("NIC555555")
                .dob(new Date())
                .mobileNo("5555555555")
                .email("regno@example.com")
                .build();
        customerDao.add(connection, customer);

        boolean exists = customerDao.existByRegNo(connection, "REG555");
        assertTrue(exists, "Customer should exist with given registration number");

        boolean notExists = customerDao.existByRegNo(connection, "NONEXISTENT");
        assertFalse(notExists, "Customer should not exist with non-existent registration number");
    }

    @Test
    void existByNic() throws SQLException, ClassNotFoundException {
        Customer customer = new Customer.CustomerBuilder()
                .registrationNo("REG666")
                .name("Nic Test")
                .address("666 Nic St")
                .nic("NIC666666")
                .dob(new Date())
                .mobileNo("6666666666")
                .email("nic@example.com")
                .build();
        customerDao.add(connection, customer);

        boolean exists = customerDao.existByNic(connection, "NIC666666");
        assertTrue(exists, "Customer should exist with given NIC");

        boolean notExists = customerDao.existByNic(connection, "NONEXISTENTNIC");
        assertFalse(notExists, "Customer should not exist with non-existent NIC");
    }
}
