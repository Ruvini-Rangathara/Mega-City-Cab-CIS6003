package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.entity.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    // Convert User (Entity) to UserDTO (DTO)
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO.UserDTOBuilder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .salt(user.getSalt())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }

    // Convert UserDTO (DTO) to User (Entity)
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return new User.UserBuilder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .salt(userDTO.getSalt())
                .role(userDTO.getRole())
                .createdAt(userDTO.getCreatedAt())
                .updatedAt(userDTO.getUpdatedAt())
                .deletedAt(userDTO.getDeletedAt())
                .build();
    }

    // Convert List of User entities to List of UserDTOs
    public static List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(UserConverter::toDTO)
                .collect(Collectors.toList());
    }

    // Added convenience method to convert List of DTOs to List of Entities
    public static List<User> toEntityList(List<UserDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        }
        return userDTOs.stream()
                .map(UserConverter::toEntity)
                .collect(Collectors.toList());
    }
}
