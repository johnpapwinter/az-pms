package com.az.azpms.domain.aspects;

import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.service.EmailService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailAspect {

    private final EmailService emailService;

    @Autowired
    public EmailAspect(EmailService emailService) {
        this.emailService = emailService;
    }


    @AfterReturning(pointcut = "execution(* com.az.azpms.service.EmailService.sendResetPasswordRequestEmail(..))",
            returning = "result")
    public void afterGenerateResetPasswordToken(Object result) {
        try {
            AzUser azUser = (AzUser) result;

            emailService.sendResetPasswordRequestEmail(azUser.getEmail(), azUser.getResetToken());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(pointcut = "execution(* com.az.azpms.service.EmailService.sendResetPasswordSuccessEmail(..))",
            returning = "result")
    public void afterPasswordResetOperation(Object result) {
        try {
            AzUser azUser = (AzUser) result;

            emailService.sendResetPasswordSuccessEmail(azUser.getEmail(), azUser.getFirstname());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
