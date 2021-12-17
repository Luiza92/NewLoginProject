package com.example.LoginDemoProject.controller;

import com.example.LoginDemoProject.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Controller
public class LogoutController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User user;


    @PostMapping(path = "/api/logout", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object logout(@RequestHeader Map<String, String> headers) throws JSONException {
        try {
            String sql = ("select * from access_token where token = ?  ;");
            System.err.println("token: " + headers.get("authorization"));
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, headers.get("authorization"));

            int refresh_token_id = (int) result.get("refresh_token_id");

            String sql1 = ("delete  from access_token where token = ?;");
            int result1 = jdbcTemplate.update(sql1, headers.get("authorization"));

            String sql2 = ("delete  from refresh_token where id = ?;");
            int result2 = jdbcTemplate.update(sql2, refresh_token_id);


            if (result1 == 0) {
                System.out.println("delete token");
            }
            if (result2 == 0) {
                System.out.println("delete refresh_token_id");
            }

            JSONObject res = new JSONObject();

            res.put("message", "this user logout ");
            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (Exception ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", " not found  " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNAUTHORIZED);
        }

    }
}



