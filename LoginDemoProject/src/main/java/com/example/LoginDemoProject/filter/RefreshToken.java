package com.example.LoginDemoProject.filter;

import com.example.LoginDemoProject.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class RefreshToken {


    public String generateRefreshToken(User userDetails, Date exDate) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), exDate);
    }

    int JWT_TOKEN_VALIDITY = 1000;

    private String doGenerateToken(Map<String, Object> claims, String subject, Date exDate) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exDate)
                .signWith(key).compact();


    }


}
