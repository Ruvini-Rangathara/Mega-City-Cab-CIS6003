package com.project.megacitycab.dto.customer;

import java.util.Date;

public class UpdateCustomerDTO extends CreateCustomerDTO {
    private String id;

    public UpdateCustomerDTO() {
    }

    public UpdateCustomerDTO(String id, String registrationNo, String name, String address, String nic, Date dob, String mobileNo, String email) {
        super(registrationNo, name, address, nic, dob, mobileNo, email);
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
        return "UpdateCustomerDTO{" +
                "id='" + id + '\'' +
                ", registrationNo='" + getRegistrationNo() + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", nic='" + getNic() + '\'' +
                ", dob='" + getDob() + '\'' +
                ", mobileNo='" + getMobileNo() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
