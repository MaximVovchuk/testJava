package com.teamvoy.testJava.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teamvoy.testJava.models.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String email, Collection<Role> roles) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(1).toInstant());
        String role = roles.toArray()[0].toString();
        return JWT.create()
                .withSubject("User details")
                .withClaim("email", email)
                .withClaim("role",role)
                .withIssuedAt(new Date())
                .withIssuer("testJavaVovchuk")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String getEmail(String token) {
        String jwt = token.substring(7).trim();
        return validateTokenAndRetrieveEmail(jwt);
    }

    public String validateTokenAndRetrieveEmail(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("testJavaVovchuk")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}