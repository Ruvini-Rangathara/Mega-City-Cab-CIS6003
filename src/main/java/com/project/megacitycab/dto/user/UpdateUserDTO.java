package com.project.megacitycab.dto.user;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dto.SuperDTO;

public class UpdateUserDTO extends CreateUserDTO implements SuperDTO {
    private String id;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(String id, String email, String password, Role role) {
        super(email, password, role);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateUserDTO{" +
                "id='" + id + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
