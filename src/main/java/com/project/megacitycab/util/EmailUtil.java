package com.project.megacitycab.util;

import com.project.megacitycab.dto.BookingDTO;

import java.text.DecimalFormat;

public class EmailUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    // ANSI escape codes for colored terminal output
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";  // Success messages and metadata
    private static final String ANSI_CYAN = "\u001B[36m";   // Email content
    private static final String ANSI_YELLOW = "\u001B[33m"; // Headers
    private static final String ANSI_BLUE = "\u001B[34m";   // Section titles

    /**
     * Prints a demo dashboard email to the terminal with colored formatting and no HTML.
     *
     * @param recipientEmail       Recipient's email address
     * @param recipientName        Recipient's name
     * @param totalRevenue         Total revenue
     * @param totalVehicleEarnings Total vehicle earnings
     * @param totalDriverEarnings  Total driver earnings
     * @param totalCustomers       Number of customers
     * @param totalBookings        Number of bookings
     * @param totalExpenses        Total expenses
     */
    public static void printDashboardEmail(String recipientEmail, String recipientName, double totalRevenue, double totalVehicleEarnings, double totalDriverEarnings, long totalCustomers, long totalBookings, double totalExpenses) {
        // Print header
        System.out.println(ANSI_YELLOW + "==========================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "       EMAIL PREVIEW (DEMO)       " + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "==========================================" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "To: " + recipientEmail + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Subject: Mega City Cab Dashboard Summary" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Date: " + new java.util.Date() + ANSI_RESET);
        System.out.println(ANSI_BLUE + "----------------------------------------" + ANSI_RESET);

        // Email body content (readable format)
        System.out.println(ANSI_CYAN + "Dear " + recipientName + "," + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Here is your latest dashboard summary as of " + new java.util.Date() + ":" + ANSI_RESET);
        System.out.println();

        // Key Metrics section
        System.out.println(ANSI_BLUE + "Key Metrics:" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "  Total Revenue:          $" + DECIMAL_FORMAT.format(totalRevenue) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "  Total Vehicle Earnings: $" + DECIMAL_FORMAT.format(totalVehicleEarnings) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "  Total Driver Earnings:  $" + DECIMAL_FORMAT.format(totalDriverEarnings) + ANSI_RESET);
        System.out.println(ANSI_CYAN + "  Total Customers:        " + totalCustomers + ANSI_RESET);
        System.out.println(ANSI_CYAN + "  Total Bookings:         " + totalBookings + ANSI_RESET);
        System.out.println(ANSI_CYAN + "  Total Expenses:         $" + DECIMAL_FORMAT.format(totalExpenses) + ANSI_RESET);
        System.out.println();

        // Footer
        System.out.println(ANSI_CYAN + "For more details, please visit the Dashboard: http://localhost:8080/megacitycab_war_exploded/dashboard" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Best regards," + ANSI_RESET);
        System.out.println(ANSI_CYAN + "The Mega City Cab Team" + ANSI_RESET);

        // Print footer
        System.out.println(ANSI_BLUE + "----------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Email preview printed successfully!" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "==========================================" + ANSI_RESET);
    }


    // New method to generate booking confirmation email body
    public static void printBookingConfirmationEmail(String recipientEmail, String recipientName, BookingDTO booking) {
        String emailBody = "Your booking has been successfully created with Mega City Cab!\n\n" +
                "Booking Details:\n" +
                "  Booking ID:       " + booking.getId() + "\n" +
                "  Customer:         " + recipientName + "\n" +
                "  Booking Date:     " + booking.getBookingDate() + "\n" +
                "  Pickup Time:      " + booking.getPickupTime() + "\n" +
                "  Release Time:     " + booking.getReleaseTime() + "\n" +
                "  Pickup Location:  " + booking.getPickupLocation() + "\n" +
                "  Destination:      " + booking.getDestination() + "\n" +
                "  Distance:         " + String.format("%.2f km", booking.getDistance()) + "\n" +
                "  Fare:             LKR " + DECIMAL_FORMAT.format(booking.getFare()) + "\n" +
                "  Discount:         LKR " + DECIMAL_FORMAT.format(booking.getDiscount()) + "\n" +
                "  Tax:              LKR " + DECIMAL_FORMAT.format(booking.getTax()) + "\n" +
                "  Net Total:        LKR " + DECIMAL_FORMAT.format(booking.getNetTotal()) + "\n" +
                "  Status:           " + booking.getStatus() + "\n\n" +
                "Thank you for choosing Mega City Cab. We look forward to serving you!\n";

        printGenericEmail(recipientEmail, "Mega City Cab Booking Confirmation", emailBody, recipientName);
    }

    /**
     * Generic method to print any email content to the terminal with no HTML.
     *
     * @param recipientEmail Recipient's email address
     * @param subject        Email subject
     * @param body           Email body (plain text)
     * @param recipientName  Recipient's name
     */
    public static void printGenericEmail(String recipientEmail, String subject, String body, String recipientName) {
        System.out.println(ANSI_YELLOW + "==========================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "       EMAIL PREVIEW (DEMO)       " + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "==========================================" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "To: " + recipientEmail + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Subject: " + subject + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Date: " + new java.util.Date() + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Recipient: " + recipientName + ANSI_RESET);
        System.out.println(ANSI_BLUE + "----------------------------------------" + ANSI_RESET);

        System.out.println(ANSI_CYAN + "Dear " + recipientName + "," + ANSI_RESET);
        System.out.println(ANSI_CYAN + body + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Best regards," + ANSI_RESET);
        System.out.println(ANSI_CYAN + "The Mega City Cab Team" + ANSI_RESET);

        System.out.println(ANSI_BLUE + "----------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Email preview printed successfully!" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "==========================================" + ANSI_RESET);
    }
}
