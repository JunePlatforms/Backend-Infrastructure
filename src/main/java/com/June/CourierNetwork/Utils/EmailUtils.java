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
        return "<a href='https://app.junelogistics.com/verifyemail?token=" + token + "'>Verify Your Account</a>";

    }

    public static String getCustomerWelcomeMessage(String name, String customerNumber) {
        return "Hey " + name + ",<br><br>" +
                "Welcome to JUNE!<br><br>" +
                "At JUNE, shipping isn't just about packages—it's about shipping your lifestyle, your essence, your vibe. " +
                "Here's your shipping address:<br><br>" +
                "<span class='bold'>6858 NW 75th St STE 4 "+customerNumber+" Miami, FL, 33166-2562</span><br><br>" +
                "Plug this address in at your favorite online checkout, and voila! Your packages are sent straight to us. " +
                "Shop globally, ship effortlessly.<br><br>" +
                "<span class='bold'>Why ship with JUNE?</span><br><br>" +
                "<ul class='bullet-points'>" +
                "<li>4x per week shipping</li>" +
                "<li>Next day expedited shipping</li>" +
                "<li>Sea freight shipping</li>" +
                "<li>Door-to-door delivery</li>" +
                "<li>Real-time package tracking</li>" +
                "</ul><br><br>" +
                "Whether it's your personal favorites, business essentials, or heartfelt gifts for your tribe, " +
                "we're here to ensure what matters to you gets to where it needs to be, hassle-free.<br><br>" +
                "Curious for more? Dive into the perks at <a href='https://junelogistics.com'>junelogistics.com</a>. " +
                "We can't wait to team up with you as you sail through life, shipping your lifestyle.<br><br>" +
                "Cheers,<br>" +
                "JUNE";
    }

    public static String getProductUpdateEmailForAdmin(String firstName, String lastName, ProductDetailsDTO productDetailsDTO) {
        return "Hey Admin,\n\n" +
                "Your warehouse partner just added the following package: \n" +
                "Customer Name: " + firstName + " " + lastName + "\n" +
                "Customer Number: " + productDetailsDTO.getCustomerNumber() + "\n" +
                "Description: " + productDetailsDTO.getDescription() + "\n" +
                "Supplier Name: " + productDetailsDTO.getSupplierName() + "\n" +
                "Weight: " + productDetailsDTO.getWeight() + "\n" +
                "\n\nBest regards,\nJUNE";
    }

    public static String getInvoiceReceivedEmail(String name, ProductDetailsDTO productDetailsDTO) {
        return "Hey "+name+",\n" +
                "\n" +
                "Thank you for uploading your invoice for " +
                productDetailsDTO.getDescription() +
                " from " +
                productDetailsDTO.getSupplierName() +
                " For all the specifics about your package, hop into your June account dashboard. " +
                "Everything you need to know is waiting for you there."
                + "\n\nCheers,\nJUNE";
    }

    public static String getProductUpdateEmail(String name, ProductDetailsDTO productDetailsDTO, PackageStatus status) {
        return switch (status) {
            case CREATED -> "Hey " + name + ",\n\nYour " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " has just arrived at our warehouse! It's ready for its next journey, heading to Jamaica " +
                    "in our upcoming shipment.\n\n" +
                    "We'd love to get the invoice for your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " to ensure smooth processing at the Jamaica Customs Agency. Your invoice is the " +
                    "official receipt confirming your order details: the item description, quantity, and " +
                    "final cost. It's usually sent to your email after making an online purchase. \n\n" +
                    "You can easily share a screenshot or attach a PDF of this receipt.\n\n" +
                    "Hop over to your dashboard, find the package tagged for invoice details, " +
                    "and hit the 'Upload Invoice' button."
                    + "\n\nCheers,\nJUNE";

            case SHIPPED -> "Hey " + name + ",\n\nExciting news! Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is officially on its  way  to Jamaica!\n\n"
                    +"For all the specifics about your package, hop into your June account dashboard."
                    + "\n\n Cheers,\nJUNE";

            case READY_FOR_DELIVERY -> "Hey " + name + ",\n\nYour " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is ready for delivery!"
                    + "\n\nCheers,\nJUNE";

            case READY_FOR_PICKUP -> "Hey " + name + ",\n\nYour " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is ready for pickup!"
                    + "\n\n Cheers,\nJUNE";

            case DELIVERED -> "Hey " + name + ",\n\nYay! Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " was successfully delivered!"
                    + "\n\nCheers,\nJUNE";

            case OUT_FOR_DELIVERY -> "Hey " + name + ",<br><br>" +
                    "Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is out for delivery! A June Driver will contact you shortly using the information " +
                    "provided on your account. Should you have any concerns regarding your delivery, " +
                    "please reach out to us at 876-284-5120 or via email at " +
                    "<a href='mailto:dispatch@junelogistics.com'>dispatch@junelogistics.com</a><br><br>" +
                    "Cheers,<br>" +
                    "JUNE";

            case PICKED_UP -> "Hey " + name + ",\n\nYour package has been collected!" +
                    " Thank you for shipping with JUNE."
                    + "\n\nRegards,\nJUNE";

            case SENT_OFF -> "Hey "+name+", \n" +
                    "\n" +
                    "Exciting news! Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is currently en route to our cargo partners, gearing up to be shipped to Jamaica.\n" +
                    "\n" +
                    "For all the specifics about your package, hop into your June account dashboard. " +
                    "Everything you need to know is waiting for you there."
                    + "\n\nCheers,\nJUNE";

            case LANDED -> "Hey "+name+",\n" +
                    "\n" +
                    "It’s here! Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " has just touched down in Jamaica and is currently making its way to the Jamaica " +
                    "Customs Agency warehouse for inspection and valuation.\n \n" +
                    "Please note that the inspection and valuation process can take up to 12 hours. " +
                    "We appreciate your patience as we ensure your package clears through seamlessly.\n" +
                    "\n" +
                    "For all the specifics about your package, hop into your June account dashboard. " +
                    "Everything you need to know is waiting for you there."
                    + "\n\nCheers,\nJUNE";
            case PROCESSING -> "Hey "+name+",\n" +
                    "\n" +
                    "Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " has arrived at our local warehouse where it will be processed and made " +
                    "ready for pick up or delivery. \n" +
                    "\n" +
                    "For all the specifics about your package, hop into your June account dashboard. " +
                    "Everything you need to know is waiting for you there."
                    + "\n\nCheers,\nJUNE";
            case TRANSIT_TO_LOCAL_WAREHOUSE -> "Hey "+name+",\n" +
                    "\n" +
                    "It’s on the move again! Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is now in transit to our local warehouse where it will be processed and made ready " +
                    "for pick up or delivery.\n" +
                    "\n" +
                    "For all the specifics about your package, hop into your June account dashboard. " +
                    "Everything you need to know is waiting for you there." +
                    "\n\nCheers,\nJUNE";
            case TAKEN_OFF -> "Hey "+name+",\n" +
                    "\n" +
                    "Your " +
                    productDetailsDTO.getDescription() +
                    " from " +
                    productDetailsDTO.getSupplierName() +
                    " is officially on its way to Jamaica!\n" +
                    "\n" +
                    "For all the specifics about your package, hop into your June account dashboard. " +
                    "Everything you need to know is waiting for you there." +
                    "\n\n Cheers,\nJUNE";
        };
    }

//    public static String getForgetPasswordEmail(String name, String host, String token) {
//        return "Hi " + name + ",\n\nFollow the link below to reset your account password at June Logistics. " +
//                "\nIf you did not request a new password, you can safely delete this email.\n\n" +
//                getVerificationUrl(token) + "\n\nBest regards,\nJUNE";
//    }

    public static String getNewCourierAdminEmail(String name, User user) {
        return "Hi " + name + ",\n\nYou have been added to the " + user.getRole() + " role." +
                "\n\nBest regards,\nJUNE";

    }

    public static String getNewCourierEmail(String name, User user) {
        return "Hi " + name + ",\n\nYour application for courier with June Courier has been approved." +
                "\n\nBest regards,\nJUNE";
    }

    public static String getInvoiceReminderEmail(String name, ProductDetailsDTO productDetailsDTO) {
        return "Hey " + name + ",\n" +
                "\n" +
                "We want to ship your " +
                productDetailsDTO.getDescription() +
                " from " +
                productDetailsDTO.getSupplierName() +
                " but we’re not seeing your invoice. Hop over to your dashboard, " +
                "find the package tagged for invoice details, and hit the 'Upload Invoice' button.\n" +
                "\n" +
                "By getting this done now, your package can join our next shipment without any delays." +
                "\n\n" +
                "Cheers,\n" +
                "JUNE";
    }
}
