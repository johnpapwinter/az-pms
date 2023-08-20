package com.az.azpms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.az.azpms.domain.entities.AzUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.issuer}")
    private String jwtIssuer;

    @Value("${app.jwt.expiration}")
    private int jwtExpirationInMs;


    public String generateJwt(Authentication authentication) {
        AzUserPrincipal userPrincipal = (AzUserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + jwtExpirationInMs))
                .withClaim("username", username)
                .withIssuer(jwtIssuer)
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    public String getUsernameFromJwt(String token) {

        return decodeJWT(token).getSubject();
    }

    public boolean validateJwt(String token) {

        return decodeJWT(token) != null;
    }

    public DecodedJWT decodeJWT(String token) {
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtIssuer)
                    .build();

            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT");
        }

        return decodedJWT;
    }
}
