package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Model.User;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

import static com.June.CourierNetwork.Utils.EmailUtils.*;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    public static final String CREATED_UPDATE = "We've Got It!";
    public static final String SHIPPED_UPDATE = "Your Package Has Taken Off!";
    public static final String READY_FOR_PICKUP_UPDATE = "It's Here!";
    public static final String OUT_FOR_DELIVERY_UPDATE = "It's On The Way!";
    public static final String DELIVERED_UPDATE = "Thanks For Shipping!";
    public static final String SENT_OFF_UPDATE = "Your Package Is On The Way!";
    public static final String LANDED_UPDATE = "Your Package Has Landed in Jamaica!";
    public static final String MISSING_INVOICE = "We Need Your Invoice!";
    public static final String WELCOME_NEW_USER = "Welcome Email";
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "emailtemplate";
    public static final String TEXT_HTML_ENCODING = "text/html";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String personalName;

    @Value("${spring.mail.verify.email.host}")
    private String host;

    @Override
    @Async
    public void sendVerificationMail(User user, String token) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
//            message.setFrom(fromEmail, personalName);
//            message.setTo(user.getEmailAddress());
//            message.setText(getEmailVerificationMessage(user, token));
//            emailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            String content = getEmailVerificationMessage(user, token);

            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail, personalName);
            helper.setTo(user.getEmailAddress());
            helper.setText(content, true);  // Set the second parameter to true for HTML content
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendCustomerWelcomeMail(String name, String to, String customerNumber) {
        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setSubject(WELCOME_NEW_USER);
//            message.setFrom(fromEmail);
//            message.setTo(to);
//            message.setText(getCustomerWelcomeMessage(name, customerNumber));
//            emailSender.send(message);
            String content = getCustomerWelcomeMessage(name, customerNumber);

            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(WELCOME_NEW_USER);
            helper.setFrom(fromEmail, personalName);
            helper.setTo(to);
            helper.setText(content, true);  // Set the second parameter to true for HTML content
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    @Async
    public void sendProductUpdateEmail(long productId) {
        productRepository.findProductById(productId).ifPresent(productDetailsDTO ->
                userRepository.findUserByCustomerNumber(productDetailsDTO.getCustomerNumber()).ifPresent(user -> {
                    try {
                        MimeMessage message = getMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
                        switch (productDetailsDTO.getPackageStatus()) {
                            case CREATED -> helper.setSubject(CREATED_UPDATE);
                            case SHIPPED -> helper.setSubject(SHIPPED_UPDATE);
                            case READY_FOR_PICKUP -> helper.setSubject(READY_FOR_PICKUP_UPDATE);
                            case OUT_FOR_DELIVERY -> helper.setSubject(OUT_FOR_DELIVERY_UPDATE);
                            case DELIVERED -> helper.setSubject(DELIVERED_UPDATE);
                            case SENT_OFF -> helper.setSubject(SENT_OFF_UPDATE);
                            case LANDED -> helper.setSubject(LANDED_UPDATE);
                        }
                        helper.setFrom(fromEmail, personalName);
                        helper.setTo(user.getEmailAddress());
                        helper.setText(getProductUpdateEmail(user.getFirstName(), productDetailsDTO, productDetailsDTO.getPackageStatus()));
                        emailSender.send(message);
                    } catch (Exception e) {
                e.printStackTrace();
            }
        }));

    }

    @Override
    @Async
    public void sendInvoiceReminderEmail(long productId) {
        productRepository.findProductById(productId).ifPresent(productDetailsDTO ->
                userRepository.findUserByCustomerNumber(productDetailsDTO.getCustomerNumber()).ifPresent(user -> {
                    if (productDetailsDTO.getPreAlertFileName() != null) {
                        return;
                    }
                    try {
                        MimeMessage message = getMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
                        helper.setSubject(MISSING_INVOICE);
                        helper.setFrom(fromEmail, personalName);
                        helper.setTo(user.getEmailAddress());
                        helper.setText(getInvoiceReminderEmail(user.getFirstName(), productDetailsDTO));
                        emailSender.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));

    }

//    @Override
//    @Async
//    public void sendMimeMessageWithAttachments(String name, String to, String token) {
//        try {
//            MimeMessage message = getMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
//            helper.setPriority(1);
//            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setText(getEmailVerificationMessage(name, host, token));
//            //Add attachments
//            FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/fort.jpg"));
//            FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.jpg"));
//            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
//            helper.addAttachment(fort.getFilename(), fort);
//            helper.addAttachment(dog.getFilename(), dog);
//            helper.addAttachment(homework.getFilename(), homework);
//            emailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    @Async
//    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
//        try {
//            MimeMessage message = getMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
//            helper.setPriority(1);
//            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setText(getEmailVerificationMessage(name, host, token));
//            //Add attachments
//            FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/fort.jpg"));
//            FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.jpg"));
//            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
//            helper.addInline(getContentId(fort.getFilename()), fort);
//            helper.addInline(getContentId(dog.getFilename()), dog);
//            helper.addInline(getContentId(homework.getFilename()), homework);
//            emailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {
//        try {
//            Context context = new Context();
//            context.setVariables(Map.of("name", name, "url", getVerificationUrl(host, token)));
//            String text = templateEngine.process(EMAIL_TEMPLATE, context);
//            MimeMessage message = getMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
//            helper.setPriority(1);
//            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setText(text, true);
//            emailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    @Async
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {
//        try {
//            MimeMessage message = getMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
//            helper.setPriority(1);
//            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            //helper.setText(text, true);
//            Context context = new Context();
//            context.setVariables(Map.of("name", name, "url", getVerificationUrl(host, token)));
//            String text = templateEngine.process(EMAIL_TEMPLATE, context);
//
//            // Add HTML email body
//            MimeMultipart mimeMultipart = new MimeMultipart("related");
//            BodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(text, TEXT_HTML_ENCODING);
//            mimeMultipart.addBodyPart(messageBodyPart);
//
//            // Add images to the email body
//            BodyPart imageBodyPart = new MimeBodyPart();
//            DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/images/dog.jpg");
//            imageBodyPart.setDataHandler(new DataHandler(dataSource));
//            imageBodyPart.setHeader("Content-ID", "image");
//            mimeMultipart.addBodyPart(imageBodyPart);
//
//            emailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    private String getContentId(String filename) {
        return "<" + filename + ">";
    }
}
