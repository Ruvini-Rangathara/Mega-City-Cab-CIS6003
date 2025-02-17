package com.project.megacitycab.dto;
import com.project.megacitycab.constant.DriverStatus;

public class DriverDTO implements SuperDTO {
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

    public DriverDTO() {
    }

    public DriverDTO(String id, String name, String licenseNo, String mobileNo, DriverStatus status, int experience, String email, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.name = name;
        this.licenseNo = licenseNo;
        this.mobileNo = mobileNo;
        this.status = status;
        this.experience = experience;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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
}
