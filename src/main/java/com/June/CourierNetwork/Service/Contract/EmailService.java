package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Model.User;

public interface EmailService {
    void sendVerificationMail(User user, String token);

    void sendWelcomeMail(String name, String to);

    void sendProductUpdateEmail(long productId);

//    void sendMimeMessageWithAttachments(String name, String to, String token);
//    void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token);
}
