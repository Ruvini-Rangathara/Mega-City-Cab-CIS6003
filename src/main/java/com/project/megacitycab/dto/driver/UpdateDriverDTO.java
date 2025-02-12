package com.project.megacitycab.dto.driver;

import com.project.megacitycab.constant.DriverStatus;
import com.project.megacitycab.dto.SuperDTO;

public class UpdateDriverDTO extends CreateDriverDTO implements SuperDTO {
    private String id;

    public UpdateDriverDTO() {
    }

    public UpdateDriverDTO(String id, String name, String licenseNo, String mobileNo, DriverStatus status, int experience, String email) {
        super(name, licenseNo, mobileNo, status, experience, email);
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
        return "UpdateDriverDTO{" +
                "id='" + id + '\'' +
                ", name='" + getName() + '\'' +
                ", licenseNo='" + getLicenseNo() + '\'' +
                ", mobileNo='" + getMobileNo() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", experience='" + getExperience() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
