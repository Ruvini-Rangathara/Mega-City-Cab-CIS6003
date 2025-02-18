package com.project.megacitycab.service.custom;


import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.service.SuperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public interface AuthService extends SuperService {

    void login(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void register(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException;

    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
