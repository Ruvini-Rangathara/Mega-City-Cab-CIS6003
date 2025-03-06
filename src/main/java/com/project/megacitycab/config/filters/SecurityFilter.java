package com.project.megacitycab.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityFilter implements Filter {
    private final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    // List of paths that don't require authentication
    private final List<String> PUBLIC_PATHS = Arrays.asList("/index.jsp", "/auth/login.jsp", "/auth/register.jsp", "/auth/login", "/auth/register", "/assets/", "/css", "/js", "/images/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String servletPath = httpRequest.getServletPath();

        // Check if the requested path is public
        boolean isPublicPath = isPublicPath(servletPath);

        // Public endpoints (no authentication required)
        if (httpRequest.getServletPath().startsWith("/auth")) {
            chain.doFilter(request, response); // Allow access to public endpoints
            return;
        }

        // Allow access to public paths for non-authenticated users
        if (isPublicPath) {
            chain.doFilter(request, response);
            return;
        }

        boolean isAuthenticated = (session != null && session.getAttribute("user") != null);
        logger.log(Level.INFO, "Authentication Status : " + isAuthenticated);
        if (!isAuthenticated) {
            logger.log(Level.SEVERE, "Security Filter : Authentication Failed. Redirecting to Login Page");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login"); // Redirect to login page
            return;
        }

        // Check user role for authorization
//        String userRole = (String) session.getAttribute("role");

        // Example: Only allow "admin" role to access /admin endpoints
//        if (servletPath.contains("/admin") && !"admin".equals(userRole)) {
//            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied"); // 403 Forbidden
//            return;
//        }

        // Allow access to the requested resource
        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> path.equals(publicPath) || (publicPath.endsWith("/") && path.startsWith(publicPath))) || path.equals("/");
    }


    @Override
    public void destroy() {
        // Cleanup logic (if needed)
    }
}
