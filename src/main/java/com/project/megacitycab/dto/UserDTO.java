package com.project.megacitycab.dto;

import com.project.megacitycab.constant.Role;
import java.util.Date;

public class UserDTO implements SuperDTO {
    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final String salt;
    private final Role role;
    private final Date createdAt;
    private final Date updatedAt;
    private final Date deletedAt;

    private UserDTO(UserDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.salt = builder.salt;
        this.role = builder.role;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    // Only getters since we're using immutable pattern
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Role getRole() {
        return role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    // Static Builder class
    public static class UserDTOBuilder {
        private String id;
        private String name;
        private String email;
        private String password;
        private String salt;
        private Role role;
        private Date createdAt;
        private Date updatedAt;
        private Date deletedAt;

        public UserDTOBuilder() {
        }

        // Builder methods for each field
        public UserDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTOBuilder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public UserDTOBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserDTOBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDTOBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserDTOBuilder deletedAt(Date deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        // Convenience method to set both created and updated dates to now
        public UserDTOBuilder setTimestampsToNow() {
            Date now = new Date();
            this.createdAt = now;
            this.updatedAt = now;
            return this;
        }

        // Build method to create the UserDTO instance
        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}
