package com.az.azpms.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendResetPasswordRequestEmail(String email, String resetToken) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        String resetPasswordUrl = "";

        Context context = new Context();
        HashMap props =new HashMap<>();
        props.put("resetPassword", resetPasswordUrl);
        context.setVariables(props);

        String html = templateEngine.process("reset-password-request", context);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject("Password Reset Request");

        messageHelper.setFrom("");
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendResetPasswordSuccessEmail(String email, String firstname) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        Context context = new Context();
        HashMap props =new HashMap<>();
        props.put("firstname", firstname);
        context.setVariables(props);

        String html = templateEngine.process("reset-password-success", context);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject("Password Reset Successfully");

        messageHelper.setFrom("");
        mailSender.send(mimeMessage);
    }

}
