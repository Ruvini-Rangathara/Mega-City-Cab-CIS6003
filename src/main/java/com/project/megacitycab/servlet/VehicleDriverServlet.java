package com.project.megacitycab.servlet;

import com.project.megacitycab.constant.VehicleStatus;
import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.custom.VehicleDriverService;
import com.project.megacitycab.service.custom.impl.VehicleDriverServiceImpl;
import com.project.megacitycab.util.SendResponse;
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

@WebServlet(name = "vehicleDriverServlet", value = "/vehicle-driver-servlet")
public class VehicleDriverServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VehicleDriverServlet.class.getName());
    private VehicleDriverService vehicleDriverService;

    @Override
    public void init() {
        vehicleDriverService = new VehicleDriverServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addVehicleDriver(request, response);
                break;
            case "update":
                updateVehicleDriver(request, response);
                break;
            case "delete":
                deleteVehicleDriver(request, response);
                break;
            case "search":
                searchVehicleDriver(request, response);
                break;
            default:
                logger.log(Level.SEVERE, "Error : Invalid Action");
                response.sendRedirect(request.getContextPath() + "/vehicle-drivers?error=Invalid action");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Create search parameters map
            Map<String, String> searchParams = new HashMap<>();
            searchParams.put("licensePlate", request.getParameter("searchLicensePlate"));
            searchParams.put("brand", request.getParameter("searchBrand"));
            searchParams.put("model", request.getParameter("searchModel"));
            searchParams.put("driverName", request.getParameter("searchDriverName"));
            searchParams.put("licenseNo", request.getParameter("searchLicenseNo"));
            searchParams.put("color", request.getParameter("searchColor"));
            searchParams.put("driverEmail", request.getParameter("searchDriverEmail"));

            // Get filtered list of vehicle-drivers
            List<VehicleDriverDTO> vehicleDrivers = vehicleDriverService.getAll(searchParams);
            request.setAttribute("vehicleDrivers", vehicleDrivers);

            // Forward to JSP page
            request.getRequestDispatcher("/views/vehicle-driver.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error retrieving vehicle-drivers: " + e.getMessage());
            request.getRequestDispatcher("/views/vehicle-driver.jsp")
                    .forward(request, response);
        }
    }

    private void addVehicleDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Add vehicle-driver request received, License Plate: " + request.getParameter("licensePlate"));
        try {
            // Create Driver DTO
            DriverDTO driverDTO = new DriverDTO.DriverDTOBuilder()
                    .name(request.getParameter("driverName"))
                    .licenseNo(request.getParameter("licenseNo"))
                    .mobileNo(request.getParameter("driverMobile"))
                    .email(request.getParameter("driverEmail"))
                    .experience(Integer.parseInt(request.getParameter("experience")))
                    .build();

            // Create Vehicle DTO
            VehicleDTO vehicleDTO = new VehicleDTO.VehicleDTOBuilder()
                    .licensePlate(request.getParameter("licensePlate"))
                    .brand(request.getParameter("brand"))
                    .model(request.getParameter("model"))
                    .color(request.getParameter("color"))
                    .capacity(Double.parseDouble(request.getParameter("capacity")))
                    .status(VehicleStatus.valueOf(request.getParameter("vehicleStatus")))
                    .build();

            // Create VehicleDriver DTO
            VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                    .driver(driverDTO)
                    .vehicle(vehicleDTO)
                    .build();

            boolean isAdded = vehicleDriverService.add(vehicleDriverDTO);

            if (isAdded) {
                response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?success=Vehicle-Driver successfully saved");
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?error=Failed to save vehicle-driver");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding vehicle-driver: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?error=" + e.getMessage());
        }
    }

    private void updateVehicleDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Update vehicle-driver request received, Vehicle ID: " + request.getParameter("vehicleId"));
        try {
            // Create Driver DTO
            DriverDTO driverDTO = new DriverDTO.DriverDTOBuilder()
                    .id(request.getParameter("driverId"))
                    .name(request.getParameter("driverName"))
                    .licenseNo(request.getParameter("licenseNo"))
                    .mobileNo(request.getParameter("driverMobile"))
                    .email(request.getParameter("driverEmail"))
                    .experience(Integer.parseInt(request.getParameter("experience")))
                    .build();

            // Create Vehicle DTO
            VehicleDTO vehicleDTO = new VehicleDTO.VehicleDTOBuilder()
                    .id(request.getParameter("vehicleId"))
                    .licensePlate(request.getParameter("licensePlate"))
                    .brand(request.getParameter("brand"))
                    .model(request.getParameter("model"))
                    .color(request.getParameter("color"))
                    .capacity(Double.parseDouble(request.getParameter("capacity")))
                    .status(VehicleStatus.valueOf(request.getParameter("vehicleStatus")))
                    .driverId(request.getParameter("driverId"))
                    .build();

            // Create VehicleDriver DTO
            VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                    .driver(driverDTO)
                    .vehicle(vehicleDTO)
                    .build();

            boolean isUpdated = vehicleDriverService.update(vehicleDriverDTO);

            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?success=Vehicle-Driver successfully updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?error=Failed to update vehicle-driver");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating vehicle-driver: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?error=" + e.getMessage());
        }
    }

    private void deleteVehicleDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Delete vehicle-driver request received, Vehicle ID: " + request.getParameter("vehicleId"));
        try {
            String vehicleId = request.getParameter("vehicleId");
            String driverId = request.getParameter("driverId");

            if (vehicleId == null || driverId == null) {
                throw new IllegalArgumentException("Both vehicle ID and driver ID are required for deletion");
            }

            boolean isDeleted = vehicleDriverService.delete(vehicleId, driverId);

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?success=Vehicle-Driver successfully deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?error=Failed to delete vehicle-driver");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting vehicle-driver: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/vehicle-driver-servlet?error=" + e.getMessage());
        }
    }

    private void searchVehicleDriver(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Search vehicle-driver request received, Vehicle ID: " + request.getParameter("vehicleId"));
        try {
            String vehicleId = request.getParameter("vehicleId");
            if (vehicleId == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Vehicle ID is required for search");
                return;
            }

            VehicleDriverDTO vehicleDriverDTO = vehicleDriverService.searchById(vehicleId);
            request.setAttribute("vehicleDriver", vehicleDriverDTO);
            request.getRequestDispatcher("/views/vehicle-driver.jsp").forward(request, response);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching vehicle-driver", e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Error fetching vehicle-driver");
        }
    }
}
