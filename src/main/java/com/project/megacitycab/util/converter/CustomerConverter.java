package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.customer.CustomerDTO;
import com.project.megacitycab.entity.Customer;

public class CustomerConverter {

    // Convert Customer (Entity) to CustomerDTO (DTO)
    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setRegistrationNo(customer.getRegistrationNo());
        customerDTO.setName(customer.getName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setNic(customer.getNic());
        customerDTO.setDob(customer.getDob());
        customerDTO.setMobileNo(customer.getMobileNo());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setCreatedAt(customer.getCreatedAt());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());
        customerDTO.setDeletedAt(customer.getDeletedAt());

        return customerDTO;
    }

    // Convert CustomerDTO (DTO) to Customer (Entity)
    public static Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setRegistrationNo(customerDTO.getRegistrationNo());
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setNic(customerDTO.getNic());
        customer.setDob(customerDTO.getDob());
        customer.setMobileNo(customerDTO.getMobileNo());
        customer.setEmail(customerDTO.getEmail());
        customer.setCreatedAt(customerDTO.getCreatedAt());
        customer.setUpdatedAt(customerDTO.getUpdatedAt());
        customer.setDeletedAt(customerDTO.getDeletedAt());

        return customer;
    }
}
