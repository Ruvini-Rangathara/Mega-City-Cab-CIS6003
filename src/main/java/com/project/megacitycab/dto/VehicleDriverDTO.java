package com.project.megacitycab.dto;

public class VehicleDriverDTO implements SuperDTO {
    private final VehicleDTO vehicle;
    private final DriverDTO driver;

    private VehicleDriverDTO(VehicleDriverDTOBuilder builder) {
        this.vehicle = builder.vehicle;
        this.driver = builder.driver;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "VehicleDriverDTO{" +
                "vehicle=" + vehicle +
                ", driver=" + driver +
                '}';
    }

    public static class VehicleDriverDTOBuilder {
        private VehicleDTO vehicle;
        private DriverDTO driver;

        public VehicleDriverDTOBuilder() {
        }

        public VehicleDriverDTOBuilder vehicle(VehicleDTO vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public VehicleDriverDTOBuilder driver(DriverDTO driver) {
            this.driver = driver;
            return this;
        }

        public VehicleDriverDTO build() {
            // Validate required fields
            if (vehicle == null) {
                throw new IllegalStateException("Vehicle cannot be null");
            }
            if (driver == null) {
                throw new IllegalStateException("Driver cannot be null");
            }
            return new VehicleDriverDTO(this);
        }
    }
}
