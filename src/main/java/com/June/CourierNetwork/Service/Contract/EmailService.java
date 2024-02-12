package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Model.User;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    void sendVerificationMail(User user, String token);

    void sendCustomerWelcomeMail(String name, String to, String customerNumber);

    void sendProductUpdateEmail(long productId);

    void sendProductUpdateEmailToAdmin(long productId);

    @Async
    void sendInvoiceReminderEmail(long productId);

//    void sendMimeMessageWithAttachments(String name, String to, String token);
//    void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token);
}
