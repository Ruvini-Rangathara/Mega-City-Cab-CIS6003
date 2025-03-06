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
import com.project.megacitycab.util.DBUtil;

import java.sql.Connection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private final Connection connection = DBUtil.getConnection();
    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = DaoFactory.getInstance().getDao(DaoTypes.USER_DAO_IMPL);
    }

    @Override
    public boolean add(UserDTO entity) throws MegaCityCabException {

        try {
            if (!validateUser(entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
            }

            if (userDAO.existByEmail(connection, entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.USER_ALREADY_EXISTS);
            }

            // Generate salt and hash password using the new method
            String salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(entity.getPassword(), salt);

            UserDTO newUser = new UserDTO.UserDTOBuilder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .password(hashedPassword)
                    .salt(salt)
                    .role(entity.getRole())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .deletedAt(entity.getDeletedAt())
                    .build();

            boolean isAdded = userDAO.add(connection, UserConverter.toEntity(newUser));
            if (!isAdded) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }

            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in add user service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean update(UserDTO entity) throws MegaCityCabException {
        try {
            if (!validateUser(entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
            }

            UserDTO originalUser = UserConverter.toDTO(userDAO.searchById(connection, entity.getId()));

            if (!Objects.equals(originalUser.getEmail(), entity.getEmail())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.CANNOT_CHANGE_EMAIL);
            }

            String newPassword;
            String newSalt;

            if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
                // Generate new salt and hash for password update
                newSalt = PasswordUtils.generateSalt();
                newPassword = PasswordUtils.hashPassword(entity.getPassword(), newSalt);
            } else {
                newPassword = originalUser.getPassword();
                newSalt = originalUser.getSalt();
            }

            UserDTO updatedUser = new UserDTO.UserDTOBuilder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .password(newPassword)
                    .salt(newSalt)
                    .role(entity.getRole() != null ? entity.getRole() : originalUser.getRole())
                    .createdAt(originalUser.getCreatedAt())
                    .updatedAt(new Date())
                    .deletedAt(originalUser.getDeletedAt())
                    .build();

            boolean isUpdated = userDAO.update(connection, UserConverter.toEntity(updatedUser));
            if (!isUpdated) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in update user service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    // Other methods remain unchanged as they don't deal with password handling
    @Override
    public boolean delete(Object... args) throws MegaCityCabException {
        try {
            if (!userDAO.existByPk(connection, args)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.USER_NOT_FOUND);
            }

            return userDAO.delete(connection, args);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in delete user service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO searchById(Object... args) throws MegaCityCabException {
        try {
            return UserConverter.toDTO(userDAO.searchById(connection, args));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in search user by ID service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserDTO> getAll(Map<String, String> searchParams) throws MegaCityCabException {
        try {
            return UserConverter.toDTOList(userDAO.getAll(connection, searchParams));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in get all users service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existByPk(Object... args) throws MegaCityCabException {
        try {
            return userDAO.existByPk(connection, args);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in exist by PK service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO searchByEmail(Object... args) throws MegaCityCabException {
        try {
            return UserConverter.toDTO(userDAO.findByEmail(connection, args));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in search by email service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateUser(UserDTO user) {
        return user.getEmail() != null &&
                user.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") &&
                user.getPassword() != null &&
                user.getPassword().length() >= 8;
    }
}
