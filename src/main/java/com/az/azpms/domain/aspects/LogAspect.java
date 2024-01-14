package com.az.azpms.domain.aspects;

import com.az.azpms.domain.dto.LoginDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    public LogAspect() {
    }

    @AfterReturning(pointcut = "execution(* com.az.azpms.controller.AuthController.login(..))", returning = "result")
    public void logLoginAttempts(JoinPoint joinPoint, ResponseEntity<?> result) {
        String username = null;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof LoginDTO) {
                username = ((LoginDTO) arg).getUsername();
                break;
            }
        }

        if (username != null) {
            logger.info("Login attempt by {}", username);
        } else {
            logger.info("Login attempt by NULL username");
        }

        if (result != null && result.getStatusCode().is2xxSuccessful()) {
            logger.info("Successful Login Attempt");
        } else {
            logger.info("Failed Login Attempt");
        }
    }

}
