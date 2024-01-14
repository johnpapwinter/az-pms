package com.az.azpms.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendResetPasswordRequestEmail(String email, String resetToken) throws MessagingException;

    void sendResetPasswordSuccessEmail(String email, String firstname) throws  MessagingException;

}
