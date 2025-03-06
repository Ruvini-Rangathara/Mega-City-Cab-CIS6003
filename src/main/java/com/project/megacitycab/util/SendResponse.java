package com.project.megacitycab.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SendResponse {
    public static void send(HttpServletResponse response, int code, String message)
            throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        String jsonResponse = String.format("{\"status\": %d, \"message\": \"%s\"}", code, message);
        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}
