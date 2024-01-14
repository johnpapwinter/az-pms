package com.az.azpms.domain.aspects;

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
    public void logLoginAttempts(ResponseEntity<?> result) {
        if (result != null && result.getStatusCode().is2xxSuccessful()) {
            logger.info("Successful Login Attempt");
        } else {
            logger.info("Failed Login Attempt");
        }
    }

}
