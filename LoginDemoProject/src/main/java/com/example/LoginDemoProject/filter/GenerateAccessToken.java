package com.example.LoginDemoProject.filter;

import com.example.LoginDemoProject.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateAccessToken {

    public String generate(User userDetails, Timestamp exDate) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), exDate);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject,Timestamp exDate) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(exDate)
                .signWith(SignatureAlgorithm.HS512, "MTIzNDU2Nzg=").compact();
    }


}
