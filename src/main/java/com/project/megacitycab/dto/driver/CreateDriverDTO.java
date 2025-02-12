package com.project.megacitycab.dto.driver;

import com.project.megacitycab.constant.DriverStatus;
import com.project.megacitycab.dto.SuperDTO;

public class CreateDriverDTO implements SuperDTO {
    private String name;
    private String licenseNo;
    private String mobileNo;
    private DriverStatus status;
    private int experience;
    private String email;

    public CreateDriverDTO() {
    }

    public CreateDriverDTO(String name, String licenseNo, String mobileNo, DriverStatus status, int experience, String email) {
        this.name = name;
        this.licenseNo = licenseNo;
        this.mobileNo = mobileNo;
        this.status = status;
        this.experience = experience;
        this.email = email;
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

    @Override
    public String toString() {
        return "CreateDriverDTO{" +
                "name='" + name + '\'' +
                ", licenseNo='" + licenseNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", status=" + status +
                ", experience=" + experience +
                ", email='" + email + '\'' +
                '}';
    }
}
