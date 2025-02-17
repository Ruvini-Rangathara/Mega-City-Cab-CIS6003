package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.util.converter.CustomerConverter;
import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = DaoFactory.getInstance().getDao(DaoTypes.CUSTOMER_DAO_IMPL);
    }

    @Override
    public boolean add(CustomerDTO entity) throws SQLException, ClassNotFoundException {
        if (!validateCustomer(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_CUSTOMER_INPUTS);
        }
        if (customerExistsByRegNo(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_ALREADY_EXISTS);
        }

        if (customerExistsByEmail(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_EMAIL_ALREADY_EXISTS);
        }

        if (customerExistsByNic(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NIC_ALREADY_EXISTS);
        }

        boolean isAdd = customerDAO.add(CustomerConverter.toEntity(entity));
        if (!isAdd) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean update(CustomerDTO entity) throws SQLException, ClassNotFoundException {

        CustomerDTO customerDTO = CustomerConverter.toDTO(customerDAO.searchById(entity.getId()));

        if (!customerExistsByPk(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NOT_FOUND);
        }

        if (!Objects.equals(customerDTO.getRegistrationNo(), entity.getRegistrationNo())) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CANNOT_CHANGE_REG_NO);
        }

        if (!validateCustomer(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_CUSTOMER_INPUTS);
        }
        if (customerExistsByEmail(entity)) {
            if(!customerDTO.getEmail().equals(entity.getEmail())){
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_EMAIL_ALREADY_EXISTS);
            }
        }

        if (customerExistsByNic(entity)) {
            if (!customerDTO.getNic().equals(entity.getNic())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NIC_ALREADY_EXISTS);
            }
        }

        boolean isUpdated = customerDAO.update(CustomerConverter.toEntity(entity));
        if (!isUpdated) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(args[0].toString());

        if (!customerExistsByPk(customerDTO)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CUSTOMER_NOT_FOUND);
        }
        return customerDAO.delete(args);
    }

    @Override
    public CustomerDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return CustomerConverter.toDTO(customerDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return CustomerConverter.toDTOList(customerDAO.getAll());

    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        return customerDAO.existByPk(args);

    }

    private boolean validateCustomer(CustomerDTO customer) {
        return customer.getEmail() != null && customer.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") && customer.getName() != null && !customer.getName().trim().isEmpty() && customer.getNic() != null && customer.getNic().matches("^(?:\\d{9}[VvXx]|\\d{12})$") && customer.getMobileNo() != null && customer.getMobileNo().matches("^(?:\\+94|0)7\\d{8}$");
    }


    private boolean customerExistsByPk(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.existByPk(customer.getId());
    }

    private boolean customerExistsByEmail(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.existByEmail(customer.getEmail());
    }

    private boolean customerExistsByRegNo(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.existByRegNo(customer.getRegistrationNo());
    }

    private boolean customerExistsByNic(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.existByNic(customer.getNic());
    }
}
