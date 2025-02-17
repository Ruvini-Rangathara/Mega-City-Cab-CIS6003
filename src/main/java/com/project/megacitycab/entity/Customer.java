package com.project.megacitycab.entity;

import java.util.Date;

public class Customer implements SuperEntity {
    private final String id;
    private final String registrationNo;
    private final String name;
    private final String address;
    private final String nic;
    private final Date dob;
    private final String mobileNo;
    private final String email;
    private final String createdAt;
    private final String updatedAt;
    private final String deletedAt;

    private Customer(CustomerBuilder builder) {
        this.id = builder.id;
        this.registrationNo = builder.registrationNo;
        this.name = builder.name;
        this.address = builder.address;
        this.nic = builder.nic;
        this.dob = builder.dob;
        this.mobileNo = builder.mobileNo;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    // Only getters to keep the object immutable
    public String getId() {
        return id;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNic() {
        return nic;
    }

    public Date getDob() {
        return dob;
    }

    public String getMobileNo() {
        return mobileNo;
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
        return "Customer{" +
                "id='" + id + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
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

    // Static Builder class
    public static class CustomerBuilder {
        private String id;
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

        public CustomerBuilder() {
        }

        public CustomerBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder registrationNo(String registrationNo) {
            this.registrationNo = registrationNo;
            return this;
        }

        public CustomerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerBuilder nic(String nic) {
            this.nic = nic;
            return this;
        }

        public CustomerBuilder dob(Date dob) {
            this.dob = dob;
            return this;
        }

        public CustomerBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public CustomerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CustomerBuilder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CustomerBuilder deletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
