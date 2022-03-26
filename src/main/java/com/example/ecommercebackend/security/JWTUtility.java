package com.example.ecommercebackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtility {

    private String SECRET_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhf3S64keuYHkLkB4IRtJQ1S2cE6F7hJE/I6xBVlMYANJggJUQUKyrfuGcGQpd6gb2ifk4XACOjRdYJx4QV/7kCAwEAAQ==";

    public String generateToken(UserDetails userDetails) throws  IllegalArgumentException, JWTDecodeException {
        return JWT.create()
                .withSubject("UserDetails")
                .withClaim("username", userDetails.getUsername())
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes()))
                .withSubject("UserDetails")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("username").asString();
    }
}
