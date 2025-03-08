package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.exception.MegaCityCabException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    private CustomerServiceImpl customerService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        customerService = new CustomerServiceImpl();
        connection = DBUtil.getConnection();
        // Clean the customers table before each test
        connection.prepareStatement("DELETE FROM customers").executeUpdate();
    }

    // Helper method to add a customer and return its ID
    private String addCustomerAndGetId(CustomerDTO customer) throws MegaCityCabException, SQLException, ClassNotFoundException {
        customerService.add(customer);
        CustomerDTO addedCustomer = customerService.searchById(customerService.getLastInsertedId(connection));
        if (addedCustomer == null || addedCustomer.getId() == null) {
            throw new RuntimeException("Failed to retrieve added customer ID");
        }
        return addedCustomer.getId();
    }

    @Test
    void add() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Test successful addition
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG001")
                .name("Test Customer")
                .address("123 Test St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("test@example.com")
                .createdAt(timestamp)
                .build();

        boolean result = customerService.add(customer);
        assertTrue(result, "Customer should be added successfully");
    }

    @Test
    void update() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a customer first
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG002")
                .name("Test Customer")
                .address("123 Test St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("test@example.com")
                .createdAt(timestamp)
                .build();
        String customerId = addCustomerAndGetId(customer);

        // Test successful update
        CustomerDTO updatedCustomer = new CustomerDTO.CustomerDTOBuilder()
                .id(customerId)
                .registrationNo("REG002")
                .name("Updated Customer")
                .address("456 Updated St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0779876543")
                .email("test@example.com")
                .createdAt(timestamp)
                .updatedAt(sdf.format(new Date()))
                .build();

        boolean result = customerService.update(updatedCustomer);
        assertTrue(result, "Customer should be updated successfully");

        CustomerDTO fetchedCustomer = customerService.searchById(customerId);
        assertEquals("Updated Customer", fetchedCustomer.getName(), "Name should be updated");
        assertEquals("456 Updated St", fetchedCustomer.getAddress(), "Address should be updated");
        assertEquals("0779876543", fetchedCustomer.getMobileNo(), "Mobile number should be updated");

      }

    @Test
    void delete() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a customer first
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG006")
                .name("Test Delete Customer")
                .address("123 Delete St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("delete@example.com")
                .createdAt(timestamp)
                .build();
        String customerId = addCustomerAndGetId(customer);

        // Test successful deletion
        boolean result = customerService.delete(customerId);
        assertTrue(result, "Customer should be deleted successfully");

        // Verify customer is deleted
        CustomerDTO deletedCustomer = customerService.searchById(customerId);
        assertEquals(null, deletedCustomer);
    }

    @Test
    void searchById() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a customer first
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG007")
                .name("Test Id Customer")
                .address("123 Id St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("id@example.com")
                .createdAt(timestamp)
                .build();
        String customerId = addCustomerAndGetId(customer);

        // Test successful search
        CustomerDTO foundCustomer = customerService.searchById(customerId);
        assertNotNull(foundCustomer, "Customer should be found by ID");
        assertEquals("Test Id Customer", foundCustomer.getName(), "Name should match");
        assertEquals("id@example.com", foundCustomer.getEmail(), "Email should match");

        // Test non-existent customer
        CustomerDTO notFoundCustomer = customerService.searchById("C999");
        assertNull(notFoundCustomer, "Non-existent customer should return null");
    }

    @Test
    void getAll() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add multiple customers
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer1 = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG008")
                .name("Customer One")
                .address("123 One St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("one@example.com")
                .createdAt(timestamp)
                .build();
        CustomerDTO customer2 = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG009")
                .name("Customer Two")
                .address("456 Two St")
                .nic("987654321V")
                .dob(new Date())
                .mobileNo("0779876543")
                .email("two@example.com")
                .createdAt(timestamp)
                .build();
        customerService.add(customer1);
        customerService.add(customer2);

        // Test getAll without search params
        List<CustomerDTO> allCustomers = customerService.getAll(null);
        assertEquals(2, allCustomers.size(), "Should return all customers");

        // Test getAll with search params (e.g., by name)
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("name", "Customer Two");
        List<CustomerDTO> filteredCustomers = customerService.getAll(searchParams);
        assertEquals(1, filteredCustomers.size(), "Should return only matching customers");
        assertEquals("Customer Two", filteredCustomers.get(0).getName(), "Name should match");
    }

    @Test
    void existByPk() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a customer first
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG010")
                .name("Test Exist Customer")
                .address("123 Exist St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("exist@example.com")
                .createdAt(timestamp)
                .build();
        String customerId = addCustomerAndGetId(customer);

        // Test existing customer
        boolean exists = customerService.existByPk(customerId);
        assertTrue(exists, "Customer should exist");

        // Test non-existent customer
        boolean notExists = customerService.existByPk("C999");
        assertFalse(notExists, "Non-existent customer should not exist");
    }

    @Test
    void getLastInsertedId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a customer first
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .registrationNo("REG011")
                .name("Test Last Id Customer")
                .address("123 Last St")
                .nic("123456789V")
                .dob(new Date())
                .mobileNo("0771234567")
                .email("lastid@example.com")
                .createdAt(timestamp)
                .build();
        customerService.add(customer);

        // Test getLastInsertedId
        String lastId = customerService.getLastInsertedId(connection);
        assertNotNull(lastId, "Last inserted ID should not be null");

        // Verify the ID corresponds to the added customer
        CustomerDTO fetchedCustomer = customerService.searchById(lastId);
        assertNotNull(fetchedCustomer, "Customer with last inserted ID should exist");
        assertEquals("Test Last Id Customer", fetchedCustomer.getName(), "Name should match");
    }
}
