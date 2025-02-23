package com.project.megacitycab.service;

import com.project.megacitycab.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public <T extends SuperService> T getService(ServiceType type) {

        SuperService service = switch (type) {
            case BOOKING_SERVICE_IMPL -> new BookingServiceImpl();
            case CUSTOMER_SERVICE_IMPL -> new CustomerServiceImpl();
            case USER_SERVICE_IMPL -> new UserServiceImpl();
            case VEHICLE_DRIVER_SERVICE_IMPL -> new VehicleDriverServiceImpl();
        };
        return (T) service;
    }
}

