package com.June.CourierNetwork.Utils;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.User;
import lombok.val;

public class EmailUtils {
    public static String getEmailVerificationMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\nYour new June Courier account has been created. Please click the link below to " +
                "verify your account. \n\n" +
                getVerificationUrl(host, token) + "\n\nThe support Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/api/users?token=" + token;
    }

    public static String getWelcomeMessage(String name) {
        return "Hello " + name + ",\n\nWelcome to June Logistics! We're thrilled to have you on board " +
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
                "\nThe June Logistics Team";
    }

    public static String getProductUpdateEmail(String name, ProductDetailsDTO productDetailsDTO, PackageStatus status) {
        return switch (status) {
            case CREATED -> "Hello " + name + ",\n\nWe have received your package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + ", from " + productDetailsDTO.getSupplierName() +
                    ", at our Florida warehouse. It is scheduled to arrive in " +
                    "Jamaica on our next available shipment. Please upload your invoice to aid in a " +
                    "speedy customs process." + "\n\nThe support Team";

            case SHIPPED -> "Hello " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    " has been shipped to " +
                    "Jamaica. Please upload your invoice to aid in a " +
                    "speedy customs process." + "\n\nThe support Team";

            case READY_FOR_PICKUP -> "Hello " + name + ",\n\nWe have received your package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    ", at our warehouse in Jamaica. Please visit our website to schedule delivery." +
                    "\n\nThe support Team";

            case DELIVERED -> "Hello " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    ", has been delivered" + ". We would love to hear your feedback. " +
                    "Please use the link below to rate your experience" + ".\n\nThe support Team";

            case OUT_FOR_DELIVERY -> "Hello " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    " has been delivered to " + productDetailsDTO.getSupplierName() + ".\n\nThe support Team";

            case PICKED_UP -> "Hello " + name + ",\n\nYour package with tracking number "
                    + productDetailsDTO.getTrackingNumber() + " from " + productDetailsDTO.getSupplierName() +
                    " has been picked up.\n\nThe support Team";
        };
    }

    public static String getForgetPasswordEmail(String name, String host, String token) {
        return "Hello " + name + ",\n\nFollow the link below to reset your customer account password at Fashion Nova. " +
                "\nIf you did not request a new password, you can safely delete this email. " +
                getVerificationUrl(host, token) + "\n\nThe support Team";
    }

    public static String getNewCourierAdminEmail(String name, User user) {
        return "Hello " + name + ",\n\nYou have been added to the " + user.getRole() + " role." +
                "\n\nThe support Team";

    }

    public static String getNewCourierEmail(String name, User user) {
        return "Hello " + name + ",\n\nYour application for courier with June Courier has been approved." +
                "\n\nThe support Team";
    }
}
