package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Override
    public void confirmationSignup(String to, String subject, String text) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("email", to);
        templateModel.put("verification_code", text);

        Context context = new Context();
        context.setVariables(templateModel);
        String htmlContent = templateEngine.process("VerificationMail", context);

        sendHtmlMessage(to, subject, htmlContent);
    }

    @Override
    public void resetPassword(String to, String text) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("email", to);
        templateModel.put("link", text);

        Context context = new Context();
        context.setVariables(templateModel);
        String htmlContent = templateEngine.process("ResetPassword", context);

        sendHtmlMessage(to, "Mot de passe oublié", htmlContent);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

}
