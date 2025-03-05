package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerConverter {

    // Convert Customer (Entity) to CustomerDTO (DTO) using Builder
    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerDTO.CustomerDTOBuilder()
                .id(customer.getId())
                .registrationNo(customer.getRegistrationNo())
                .name(customer.getName())
                .address(customer.getAddress())
                .nic(customer.getNic())
                .dob(customer.getDob())
                .mobileNo(customer.getMobileNo())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .deletedAt(customer.getDeletedAt())
                .build();
    }

    // Convert CustomerDTO (DTO) to Customer (Entity) using Builder
    public static Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        return new Customer.CustomerBuilder()
                .id(customerDTO.getId())
                .registrationNo(customerDTO.getRegistrationNo())
                .name(customerDTO.getName())
                .address(customerDTO.getAddress())
                .nic(customerDTO.getNic())
                .dob(customerDTO.getDob())
                .mobileNo(customerDTO.getMobileNo())
                .email(customerDTO.getEmail())
                .createdAt(customerDTO.getCreatedAt())
                .updatedAt(customerDTO.getUpdatedAt())
                .deletedAt(customerDTO.getDeletedAt())
                .build();
    }

    // Convert a list of Customer entities to a list of CustomerDTOs
    public static List<CustomerDTO> toDTOList(List<Customer> customers) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        if (customers != null) {
            for (Customer customer : customers) {
                customerDTOs.add(toDTO(customer));
            }
        }
        return customerDTOs;
    }
}
