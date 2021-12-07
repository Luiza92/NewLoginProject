package com.example.LoginDemoProject.filter;

import com.example.LoginDemoProject.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateAccessToken {

    public String generate(User userDetails, Date exDate) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), exDate);
    }

    int JWT_TOKEN_VALIDITY = 60;

    private String doGenerateToken(Map<String, Object> claims, String subject, Date exDate) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exDate)
                .signWith(key).compact();
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer");
    }

}
