package com.project.megacitycab.dto;

public class DriverDTO implements SuperDTO {
    private final String id;
    private final String name;
    private final String licenseNo;
    private final String mobileNo;
    private final int experience;
    private final String email;
    private final String createdAt;
    private final String updatedAt;
    private final String deletedAt;

    private DriverDTO(DriverDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.licenseNo = builder.licenseNo;
        this.mobileNo = builder.mobileNo;
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
        return "DriverDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", licenseNo='" + licenseNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", experience=" + experience +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }

    // Builder class for DriverDTO
    public static class DriverDTOBuilder {
        private String id;
        private String name;
        private String licenseNo;
        private String mobileNo;
        private int experience;
        private String email;
        private String createdAt;
        private String updatedAt;
        private String deletedAt;

        public DriverDTOBuilder() {
        }

        public DriverDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DriverDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DriverDTOBuilder licenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
            return this;
        }

        public DriverDTOBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public DriverDTOBuilder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public DriverDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public DriverDTOBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DriverDTOBuilder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public DriverDTOBuilder deletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public DriverDTO build() {
            return new DriverDTO(this);
        }
    }
}
