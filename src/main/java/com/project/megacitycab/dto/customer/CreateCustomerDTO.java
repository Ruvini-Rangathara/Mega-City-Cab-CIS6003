package com.project.megacitycab.dto.customer;

import com.project.megacitycab.dto.SuperDTO;

import java.util.Date;

public class CreateCustomerDTO implements SuperDTO {
    private String registrationNo;
    private String name;
    private String address;
    private String nic;
    private Date dob;
    private String mobileNo;
    private String email;

    public CreateCustomerDTO() {
    }

    public CreateCustomerDTO(String registrationNo, String name, String address, String nic, Date dob, String mobileNo, String email) {
        this.registrationNo = registrationNo;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.dob = dob;
        this.mobileNo = mobileNo;
        this.email = email;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CreateCustomerDTO{" +
                "registrationNo='" + registrationNo + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", dob=" + dob +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
