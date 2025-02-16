package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.user.UserDTO;
import com.project.megacitycab.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    // Convert User (Entity) to UserDTO (DTO)
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setDeletedAt(user.getDeletedAt());
        return userDTO;
    }

    // Convert UserDTO (DTO) to User (Entity)
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        user.setDeletedAt(userDTO.getDeletedAt());
        return user;
    }

//    To DtoList
    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(UserConverter::toDTO).collect(Collectors.toList());
    }
}

