package com.project.megacitycab.entity;

import com.project.megacitycab.constant.DriverStatus;

public class Driver implements SuperEntity {
    private final String id;
    private final String name;
    private final String licenseNo;
    private final String mobileNo;
    private final DriverStatus status;
    private final int experience;
    private final String email;
    private final String createdAt;
    private final String updatedAt;
    private final String deletedAt;

    private Driver(DriverBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.licenseNo = builder.licenseNo;
        this.mobileNo = builder.mobileNo;
        this.status = builder.status;
        this.experience = builder.experience;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public int getExperience() {
        return experience;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", licenseNo='" + licenseNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", status=" + status +
                ", experience=" + experience +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }

    // Static Builder class for Driver
    public static class DriverBuilder {
        private String id;
        private String name;
        private String licenseNo;
        private String mobileNo;
        private DriverStatus status;
        private int experience;
        private String email;
        private String createdAt;
        private String updatedAt;
        private String deletedAt;

        public DriverBuilder() {
        }

        public DriverBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DriverBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DriverBuilder licenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
            return this;
        }

        public DriverBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public DriverBuilder status(DriverStatus status) {
            this.status = status;
            return this;
        }

        public DriverBuilder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public DriverBuilder email(String email) {
            this.email = email;
            return this;
        }

        public DriverBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DriverBuilder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public DriverBuilder deletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
}
