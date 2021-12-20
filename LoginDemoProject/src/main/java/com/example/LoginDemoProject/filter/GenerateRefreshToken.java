package com.example.LoginDemoProject.filter;

import com.example.LoginDemoProject.model.User;

import java.security.MessageDigest;
import java.sql.Timestamp;



public class GenerateRefreshToken {

    public String generate(User modelTO, Timestamp exDate) {
        try {
            System.err.println(exDate.toString());
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] timestamp = md.digest(exDate.toString().getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < timestamp.length; i++) {
                sb.append(Integer.toString((timestamp[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println(sb);
            return sb.toString();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
}






