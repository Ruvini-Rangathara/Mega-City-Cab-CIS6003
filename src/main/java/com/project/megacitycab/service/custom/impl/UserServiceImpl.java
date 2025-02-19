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
import java.util.Date;
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

        // Generate salt and hash password
        byte[] salt = PasswordUtils.generateSalt();
        String encodedSalt = PasswordUtils.bytesToHex(salt);  // Store salt as HEX
        String hashedPassword = PasswordUtils.hashPassword(entity.getPassword(), salt);

        // Create a new UserDTO using the builder pattern
        UserDTO newUser = new UserDTO.UserDTOBuilder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(hashedPassword)
                .salt(encodedSalt)
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();

        boolean isAdded = userDAO.add(UserConverter.toEntity(newUser));
        if (!isAdded) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean update(UserDTO entity) throws SQLException, ClassNotFoundException {
        if (!validateUser(entity)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
        }

        // Retrieve the existing user from the database
        UserDTO originalUser = UserConverter.toDTO(userDAO.searchById(entity.getId()));

        if (!Objects.equals(originalUser.getEmail(), entity.getEmail())) {
            throw new MegaCityCabException(MegaCityCabExceptionType.CANNOT_CHANGE_EMAIL);
        }

        // Determine whether to generate a new password and salt
        String newPassword;
        String newSalt;

        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            byte[] salt = PasswordUtils.generateSalt();
            newPassword = PasswordUtils.hashPassword(entity.getPassword(), salt);
            newSalt = Base64.getEncoder().encodeToString(salt);
        } else {
            newPassword = originalUser.getPassword();
            newSalt = originalUser.getSalt();
        }

        // Build the updated UserDTO
        UserDTO updatedUser = new UserDTO.UserDTOBuilder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(newPassword)
                .salt(newSalt)
                .role(entity.getRole() != null ? entity.getRole() : originalUser.getRole())
                .createdAt(originalUser.getCreatedAt())
                .updatedAt(new Date()) // Update timestamp
                .deletedAt(originalUser.getDeletedAt())
                .build();

        boolean isUpdated = userDAO.update(UserConverter.toEntity(updatedUser));
        if (!isUpdated) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        if (!userExistsByPk(UserConverter.toDTO(userDAO.searchById(args[0])))) {
            throw new MegaCityCabException(MegaCityCabExceptionType.USER_NOT_FOUND);
        }
        return userDAO.delete(args);
    }

    @Override
    public UserDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return UserConverter.toDTO(userDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return UserConverter.toDTOList(userDAO.getAll());

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

    @Override
    public UserDTO searchByEmail(Object... args) throws SQLException, ClassNotFoundException {
        try {
            return UserConverter.toDTO(userDAO.findByEmail(args));
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
