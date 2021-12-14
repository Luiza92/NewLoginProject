package com.example.LoginDemoProject.controller;


import com.example.LoginDemoProject.filter.GenerateAccessToken;
import com.example.LoginDemoProject.filter.GenerateRefreshToken;
import com.example.LoginDemoProject.model.AccessToken;
import com.example.LoginDemoProject.model.RefreshToken;
import com.example.LoginDemoProject.model.User;
import com.example.LoginDemoProject.service.AccessTokenService;
import com.example.LoginDemoProject.service.RefreshTokenService;
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
import java.util.Calendar;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;
    User user = new User();
    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    private GenerateAccessToken generateAccessToken = new GenerateAccessToken();
    private GenerateRefreshToken generateRefreshToken = new GenerateRefreshToken();


    @PostMapping(path = "/api/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
                Date currentDate = new Date(System.currentTimeMillis());


                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DATE, (30));
                Date accessTokenDateExDate = new Date(c.getTimeInMillis());


                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE, (90));
                Date refreshTokenDateExDate = new Date(c.getTimeInMillis());


                String accessToken = generateAccessToken.generate(modelTO, accessTokenDateExDate);
                String refreshToken = generateRefreshToken.generate(modelTO, refreshTokenDateExDate);
                System.err.println(accessToken);
                System.err.println(refreshToken);

                RefreshToken refreshToken1 = new RefreshToken();

                refreshToken1.setUser_id(user.getId());
                refreshToken1.setToken(refreshToken);
                refreshToken1.setCreate(currentDate);
                refreshToken1.setExpires(refreshTokenDateExDate);

                System.err.println("current" + currentDate);
                System.err.println("kkkkkk" + accessTokenDateExDate);

                int rid = this.refreshTokenService.insert(refreshToken1);
                System.err.println(this.refreshTokenService.get(1));

                System.err.println(rid);


                AccessToken accessToken1 = new AccessToken();

                accessToken1.setUser_id(user.getId());
                accessToken1.setToken(accessToken);
                accessToken1.setCreate(currentDate);
                accessToken1.setExpires(accessTokenDateExDate);
                accessToken1.setRefresh_token_id(rid);

                int AccessToken = this.accessTokenService.insert(accessToken1);
                System.err.println(this.accessTokenService.get(1));

                JSONObject rest = new JSONObject();

                rest.put("user_id", accessToken1.getUser_id());
                rest.put("access_token", accessToken1.getToken());
                rest.put("Create", accessToken1.getCreate());
                rest.put("expires", accessToken1.getExpires());

                rest.put("refresh_token", refreshToken);

                return new ResponseEntity<>(rest.toString(), HttpStatus.OK);
            } else {
                res.put("message", "invalid password ");
                return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject res = new JSONObject();
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
