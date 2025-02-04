package com.project.megacitycab.model;

import java.util.Date;

public class Customer {
    private String registrationNo;
    private String name;
    private String address;
    private String nic;
    private Date dob;
    private String mobileNo;
    private String email;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public Customer() {
    }

    public Customer(String registrationNo, String name, String address, String nic, Date dob, String mobileNo, String email, String createdAt, String updatedAt, String deletedAt) {
        this.registrationNo = registrationNo;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.dob = dob;
        this.mobileNo = mobileNo;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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
        return "Customer{" +
                "registrationNo='" + registrationNo + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", dob=" + dob +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
