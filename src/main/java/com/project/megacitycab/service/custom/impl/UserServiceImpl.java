package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.UserDAO;
import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.service.custom.UserService;
import com.project.megacitycab.util.converter.UserConverter;
import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;
import com.project.megacitycab.util.security.PasswordUtils;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = DaoFactory.getInstance().getDao(DaoTypes.USER_DAO_IMPL);
    }

    @Override
    public boolean add(UserDTO entity) throws SQLException, ClassNotFoundException {
        if (!validateUser(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
        }
        if (userExistsByEmail(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.USER_ALREADY_EXISTS);
        }

        byte[] salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(entity.getPassword(), salt);

        // Set the hashed password and salt
        entity.setPassword(hashedPassword);
        entity.setSalt(Base64.getEncoder().encodeToString(salt));
        boolean isAdd = userDAO.add(UserConverter.toEntity(entity));
        if (!isAdd) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean update(UserDTO entity) throws SQLException, ClassNotFoundException {
        if (!validateUser(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
        }

        UserDTO userDTO = UserConverter.toDTO(userDAO.searchById(entity.getId()));
        if(!Objects.equals(userDTO.getEmail(), entity.getEmail())){
            throw new MegaCityCabException(MegaCityCabExceptionType.CANNOT_CHANGE_EMAIL);
        }

        // Hash the new password if provided
        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            byte[] salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(entity.getPassword(), salt);

            entity.setPassword(hashedPassword);
            entity.setSalt(Base64.getEncoder().encodeToString(salt));
        }

        boolean isUpdated = userDAO.update(UserConverter.toEntity(entity));
        if (!isUpdated) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean delete(Object... args) {
        try {
            if (!userExistsByPk(UserConverter.toDTO(userDAO.searchById(args[0])))) {
                throw new MegaCityCabException(MegaCityCabExceptionType.USER_NOT_FOUND);
            }
            return userDAO.delete(args);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new MegaCityCabException(MegaCityCabExceptionType.SQL_EXCEPTION);
            }
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO searchById(Object... args) {
        try {
            return UserConverter.toDTO(userDAO.searchById(args));
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new MegaCityCabException(MegaCityCabExceptionType.SQL_EXCEPTION);
            }
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List getAll() {
        try {
            return UserConverter.toDTOList(userDAO.getAll());
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new MegaCityCabException(MegaCityCabExceptionType.SQL_EXCEPTION);
            }
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existByPk(Object... args) {
        try {
            return userDAO.existByPk(args);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new MegaCityCabException(MegaCityCabExceptionType.SQL_EXCEPTION);
            }
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateUser(UserDTO user) {
        return user.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") && user.getPassword().length() >= 8;
    }

    private boolean userExistsByPk(UserDTO user) throws SQLException, ClassNotFoundException {
        return userDAO.existByPk(user.getId());
    }

    private boolean userExistsByEmail(UserDTO user) throws SQLException, ClassNotFoundException {
        return userDAO.existByEmail(user.getEmail());
    }
}
