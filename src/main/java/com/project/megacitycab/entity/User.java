package com.project.megacitycab.entity;

import com.project.megacitycab.constant.Role;
import java.util.Date;

public class User implements SuperEntity {
    private final String id;
    private final String email;
    private final String password;
    private final Role role;
    private final Date createdAt;
    private final Date updatedAt;
    private final Date deletedAt;

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    // Only getters, no setters as the class is immutable
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    // Static Builder class
    public static class UserBuilder {
        private String id;
        private String email;
        private String password;
        private Role role;
        private Date createdAt;
        private Date updatedAt;
        private Date deletedAt;

        public UserBuilder() {
        }

        public UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserBuilder deletedAt(Date deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
