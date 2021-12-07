package com.example.LoginDemoProject.controller;


import com.example.LoginDemoProject.filter.GenerateAccessToken;
import com.example.LoginDemoProject.filter.RefreshToken;
import com.example.LoginDemoProject.model.AccessToken;
import com.example.LoginDemoProject.model.User;
import com.example.LoginDemoProject.service.AccessTokenService;
import com.example.LoginDemoProject.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;
    User user = new User();

    private AccessTokenService accessTokenService;

    private GenerateAccessToken generateAccessToken = new GenerateAccessToken();
    private RefreshToken generateRefreshToken = new RefreshToken();


    @PostMapping(path = "/api/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> login(@ModelAttribute User modelTO) throws JSONException {
        try {
            JSONObject res = new JSONObject();

            String username = modelTO.getUsername();
            String password = modelTO.getPassword();

            User user = this.userService.getByUsername(username);
            if (user == null) {
                res.put("message", "invalid name");
                return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            String hasedPassword = user.getPassword();

            if (BCrypt.checkpw(password, hasedPassword)) {
                Date accessTokenDateExDate = new Date(System.currentTimeMillis() + 11 * 1000);
                Date refreshTokenDateExDate = new Date(System.currentTimeMillis() + 11 * 1000);

                Date currentDate = new Date(System.currentTimeMillis());

                String accessToken = generateAccessToken.generate(modelTO, accessTokenDateExDate);
                String refreshToken = generateRefreshToken.generateRefreshToken(modelTO, refreshTokenDateExDate);
                System.err.println(accessToken);
                System.err.println(refreshToken);

                AccessToken accessToken1 = new AccessToken();

                accessToken1.setUser_id(user.getId());
                accessToken1.setToken(accessToken);
                accessToken1.setCreate(currentDate);
                accessToken1.setExpires(accessTokenDateExDate);
                accessToken1.setRefresh_token(refreshToken);

                int AccessToken = this.accessTokenService.insert(accessToken1);
                System.err.println(this.accessTokenService.get(1));

                JSONObject rest = new JSONObject();

                rest.put("accessToken", "this.accessTokenService.get(1)");
                return new ResponseEntity<>(rest, HttpStatus.OK);

            } else {
                res.put("message", "invalid password ");
                return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject res = new JSONObject();
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
