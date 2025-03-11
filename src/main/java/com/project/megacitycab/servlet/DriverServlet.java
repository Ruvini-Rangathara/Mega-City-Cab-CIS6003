package com.project.megacitycab.servlet;

import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.service.ServiceFactory;
import com.project.megacitycab.service.ServiceType;
import com.project.megacitycab.service.custom.VehicleDriverService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "driverServlet", value = "/driver-servlet")
public class DriverServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DriverServlet.class.getName());
    private VehicleDriverService vehicleDriverService ;

    @Override
    public void init() {
        vehicleDriverService = ServiceFactory.getInstance().getService(ServiceType.VEHICLE_DRIVER_SERVICE_IMPL);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Create search parameters map, only adding non-empty values
            Map<String, String> searchParams = new HashMap<>();
            String searchLicenseNo = request.getParameter("searchLicenseNo");
            String searchName = request.getParameter("searchName");
            String searchEmail = request.getParameter("searchEmail");
            String searchMobile = request.getParameter("searchMobile");

            // Only add parameters if they are non-null and non-empty
            if (searchLicenseNo != null && !searchLicenseNo.trim().isEmpty()) {
                searchParams.put("licenseNo", searchLicenseNo.trim());
            }
            if (searchName != null && !searchName.trim().isEmpty()) {
                searchParams.put("name", searchName.trim());
            }
            if (searchEmail != null && !searchEmail.trim().isEmpty()) {
                searchParams.put("email", searchEmail.trim());
            }
            if (searchMobile != null && !searchMobile.trim().isEmpty()) {
                searchParams.put("mobileNo", searchMobile.trim());
            }

            // Get filtered list of drivers
            List<DriverDTO> drivers = vehicleDriverService.getAllDrivers(searchParams);
            request.setAttribute("drivers", drivers);

            // Forward to JSP page
            request.getRequestDispatcher("/views/driver.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving drivers: " + e.getMessage());
            request.setAttribute("error", "Error retrieving drivers: " + e.getMessage());
            request.getRequestDispatcher("/views/driver.jsp")
                    .forward(request, response);
        }
    }
}
