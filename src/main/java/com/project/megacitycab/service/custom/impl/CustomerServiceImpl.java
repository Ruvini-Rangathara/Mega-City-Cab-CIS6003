package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.converter.CustomerConverter;
import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());
    private final Connection connection = DBUtil.getConnection();
    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = DaoFactory.getInstance().getDao(DaoTypes.CUSTOMER_DAO_IMPL);
    }

    @Override
    public boolean add(CustomerDTO entity) throws MegaCityCabException {
        try {
            if (!validateCustomer(entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_CUSTOMER_INPUTS);
            }

            if (customerDAO.existByRegNo(connection, entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_ALREADY_EXISTS);
            }

            if (customerDAO.existByEmail(connection, entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_EMAIL_ALREADY_EXISTS);
            }

            if (customerDAO.existByNic(connection, entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NIC_ALREADY_EXISTS);
            }

            boolean isAdded = customerDAO.add(connection, CustomerConverter.toEntity(entity));
            if (!isAdded) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }

            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in add customer service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean update(CustomerDTO entity) throws MegaCityCabException {
        try {
            if (!validateCustomer(entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_CUSTOMER_INPUTS);
            }

            if (!customerDAO.existByPk(connection, entity.getId())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NOT_FOUND);
            }

            CustomerDTO originalCustomer = CustomerConverter.toDTO(customerDAO.searchById(connection, entity.getId()));

            if (!Objects.equals(originalCustomer.getRegistrationNo(), entity.getRegistrationNo())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CANNOT_CHANGE_REG_NO);
            }

            // Check email uniqueness only if email is being changed
            if (!originalCustomer.getEmail().equals(entity.getEmail()) && customerDAO.existByEmail(connection, entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_EMAIL_ALREADY_EXISTS);
            }

            // Check NIC uniqueness only if NIC is being changed
            if (!originalCustomer.getNic().equals(entity.getNic()) && customerDAO.existByNic(connection, entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NIC_ALREADY_EXISTS);
            }

            boolean isUpdated = customerDAO.update(connection, CustomerConverter.toEntity(entity));
            if (!isUpdated) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in update customer service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delete(Object... args) throws MegaCityCabException {
        try {
            if (!customerDAO.existByPk(connection, args[0])) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NOT_FOUND);
            }

            return customerDAO.delete(connection, args);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in delete customer service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CustomerDTO searchById(Object... args) throws MegaCityCabException {
        try {
            return CustomerConverter.toDTO(customerDAO.searchById(connection, args));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in search customer by ID service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CustomerDTO> getAll(Map<String, String> searchParams) throws MegaCityCabException {
        try {
            // Validate and clean search parameters
            Map<String, String> cleanParams = new HashMap<>();
            if (searchParams != null) {
                searchParams.forEach((key, value) -> {
                    if (value != null && !value.trim().isEmpty()) {
                        cleanParams.put(key, value.trim());
                    }
                });
            }

            return CustomerConverter.toDTOList(customerDAO.getAll(connection, cleanParams));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in get all customers service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existByPk(Object... args) throws MegaCityCabException {
        try {
            return customerDAO.existByPk(connection, args);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in exist by PK service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateCustomer(CustomerDTO customer) {
        return customer.getEmail() != null && customer.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") && customer.getName() != null && !customer.getName().trim().isEmpty() && customer.getNic() != null && customer.getNic().matches("^(?:\\d{9}[VvXx]|\\d{12})$") && customer.getMobileNo() != null && customer.getMobileNo().matches("^(?:\\+94|0)7\\d{8}$");
    }
}
