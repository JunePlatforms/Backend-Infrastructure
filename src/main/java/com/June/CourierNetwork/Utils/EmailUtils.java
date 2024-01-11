package com.June.CourierNetwork.Utils;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Model.User;

public class EmailUtils {
    public static String getEmailVerificationMessage(User user, String token) {
        return "Hi " + user.getFirstName() + ",<br/><br/>Your new June "+ user.getRole().name()+" account has been created. " +
                "Please click the link below to " +
                "verify your account. This must be done before you are able to access your account. <br/><br/>" +
                getVerificationUrl(token) + "<br/><br/>Best regards,<br/>JUNE";
    }

    public static String getVerificationUrl(String token) {
        return "<a href=\"https://app.junelogistics.com/verifyemail?token=" + token + "\">Verify Your Account</a>";

    }

    public static String getWelcomeMessage(String name) {
        return "Hi " + name + ",\n\nWelcome to June Logistics! We're thrilled to have you on board " +
                "and want to express our gratitude for choosing us to handle your shipping needs. " +
                "Whether you're shipping personal items, business packages, or gifts to loved ones, " +
                "we're here to make the process seamless and stress-free.\n" +
                "\n" +
                "Here's a quick overview of what you can expect as a valued member of the June Logistics family:\n" +
                "\n" +
                "1. Simple and Efficient Shipping:\n" +
                "Our user-friendly platform is designed to make shipping a breeze. " +
                "Easily schedule pickups, track your shipments in real-time, and enjoy transparent pricing.\n" +
                "\n" +
                "2. Reliable Delivery Services:\n" +
                "Rest assured that your packages are in safe hands. Our reliable and efficient delivery " +
                "network ensures that your shipments reach their destination on time and in excellent " +
                "condition. \n" +
                "\n" +
                "Thank you once again for choosing June Logistics. We're committed to providing " +
                "you with top-notch shipping services, and we look forward to serving you for all your " +
                "shipping needs.\n" +
                "\n" +
                "Safe shipping and happy travels!\n" +
                "\n" +
                "Best regards," +
                "\nJUNE";
    }

    public static String getProductUpdateEmail(String name, ProductDetailsDTO productDetailsDTO, PackageStatus status) {
        return switch (status) {
            case CREATED -> "Hi " + name + ",\n\nWe have received your package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + ", from " + productDetailsDTO.getSupplierName() +
                    ", at our Florida warehouse. It is scheduled to arrive in " +
                    "Jamaica on our next available shipment. Please upload your invoice to aid in a " +
                    "speedy customs process." + "\n\nBest regards,\nJUNE";

            case SHIPPED -> "Hi " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    " has been shipped to " +
                    "Jamaica. Please upload your invoice to aid in a " +
                    "speedy customs process." + "\n\nBest regards,\nJUNE";

            case READY_FOR_PICKUP -> "Hi " + name + ",\n\nWe have received your package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    ", at our warehouse in Jamaica. Please visit our website to schedule delivery." +
                    "\n\nBest regards,\nJUNE";

            case DELIVERED -> "Hi " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    ", has been delivered" + ". We would love to hear your feedback. " +
                    "Please use the link below to rate your experience" + ".\n\nBest regards,\nJUNE";

            case OUT_FOR_DELIVERY -> "Hi " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    " has been delivered to " + productDetailsDTO.getSupplierName() + ".\n\nBest regards,\nJUNE";

            case PICKED_UP -> "Hi " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    " has been picked up.\n\nBest regards,\nJUNE";
        };
    }

    public static String getForgetPasswordEmail(String name, String token) {
        return "Hi " + name + ",\n\nFollow the link below to reset your account password at June Logistics. " +
                "\nIf you did not request a new password, you can safely delete this email.\n\n" +
                getVerificationUrl(token) + "\n\nBest regards,\nJUNE";
    }

    public static String getNewCourierAdminEmail(String name, User user) {
        return "Hi " + name + ",\n\nYou have been added to the " + user.getRole() + " role." +
                "\n\nBest regards,\nJUNE";

    }

    public static String getNewCourierEmail(String name, User user) {
        return "Hi " + name + ",\n\nYour application for courier with June Courier has been approved." +
                "\n\nBest regards,\nJUNE";
    }
}
