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
    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = DaoFactory.getInstance().getDao(DaoTypes.USER_DAO_IMPL);
    }

    @Override
    public boolean add(UserDTO entity) throws MegaCityCabException {
        Connection connection = null;
        try {
            if (!validateUser(entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
            }

            connection = DBUtil.getConnection();

            if (userExistsByEmail(connection, entity)) {
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

            connection.commit();
            return true;
        } catch (Exception e) {
            DBUtil.rollback(connection);
            logger.log(Level.SEVERE, "Error in add user service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean update(UserDTO entity) throws MegaCityCabException {
        Connection connection = null;
        try {
            if (!validateUser(entity)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_USER_INPUTS);
            }

            connection = DBUtil.getConnection();

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

            connection.commit();
            return true;
        } catch (Exception e) {
            DBUtil.rollback(connection);
            logger.log(Level.SEVERE, "Error in update user service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    // Other methods remain unchanged as they don't deal with password handling
    @Override
    public boolean delete(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();

            if (!userExistsByPk(connection, args[0])) {
                throw new MegaCityCabException(MegaCityCabExceptionType.USER_NOT_FOUND);
            }

            boolean result = userDAO.delete(connection, args);
            connection.commit();
            return result;
        } catch (Exception e) {
            DBUtil.rollback(connection);
            logger.log(Level.SEVERE, "Error in delete user service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public UserDTO searchById(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return UserConverter.toDTO(userDAO.searchById(connection, args));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in search user by ID service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public List<UserDTO> getAll(Map<String, String> searchParams) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return UserConverter.toDTOList(userDAO.getAll(connection, searchParams));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in get all users service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean existByPk(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return userDAO.existByPk(connection, args);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in exist by PK service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public UserDTO searchByEmail(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return UserConverter.toDTO(userDAO.findByEmail(connection, args));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in search by email service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    private boolean validateUser(UserDTO user) {
        return user.getEmail() != null &&
                user.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") &&
                user.getPassword() != null &&
                user.getPassword().length() >= 8;
    }

    private boolean userExistsByPk(Connection connection, Object id) throws MegaCityCabException {
        try {
            return userDAO.existByPk(connection, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking user exists by PK", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean userExistsByEmail(Connection connection, UserDTO user) throws MegaCityCabException {
        try {
            return userDAO.existByEmail(connection, user.getEmail());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking user exists by email", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }
}
