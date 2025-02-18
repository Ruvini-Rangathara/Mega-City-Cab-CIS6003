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
            case AUTH_SERVICE_IMPL -> new AuthServiceImpl();
            case BOOKING_SERVICE_IMPL -> new BookingServiceImpl();
            case CUSTOMER_SERVICE_IMPL -> new CustomerServiceImpl();
            case DRIVER_SERVICE_IMPL -> new DriverServiceImpl();
            case USER_SERVICE_IMPL -> new UserServiceImpl();
            case VEHICLE_SERVICE_IMPL -> new VehicleServiceImpl();
        };
        return (T) service;
    }
}

