package com.project.megacitycab.dto.user;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dto.SuperDTO;

public class CreateUserDTO implements SuperDTO {
    private String email;
    private String password;
    private Role role;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
