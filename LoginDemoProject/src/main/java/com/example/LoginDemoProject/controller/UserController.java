package com.example.LoginDemoProject.controller;

import com.example.LoginDemoProject.model.User;
import com.example.LoginDemoProject.service.AccessTokenService;
import com.example.LoginDemoProject.service.RefreshTokenService;
import com.example.LoginDemoProject.service.UserService;
import com.example.LoginDemoProject.validation.UserValidation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    User user = new User();

    UserValidation userValidation = new UserValidation();


    @PostMapping(path = "/api/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser1(@ModelAttribute User modelTO) throws JSONException {
        JSONObject res = new JSONObject();
        try {

            if (userValidation.isValidUsername(modelTO.getUsername()) == false) {
                res.put("error_message", "Error Invalid Username ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidFirstName(modelTO.getFirstName()) == false) {
                res.put("error_message", "Error Invalid FirstName ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidLastName(modelTO.getLastName()) == false) {
                res.put("error_message", "Error Invalid LastName ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidEmail(modelTO.getEmail()) == false) {
                res.put("error_message", "Error Invalid Email");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidPassword(modelTO.getPassword()) == false) {
                res.put("error_message", "Error Invalid Password");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);

            }

            System.err.println("username - " + modelTO.getUsername());
            System.err.println("firstName - " + modelTO.getFirstName());
            System.err.println("lastName - " + modelTO.getLastName());
            System.err.println("email - " + modelTO.getEmail());
            System.err.println("password  - " + modelTO.getPassword());
            System.err.println("status - " + modelTO.getStatus());


            User user = new User();

            user.setUsername(modelTO.getUsername());
            user.setFirstName(modelTO.getFirstName());
            user.setLastName(modelTO.getLastName());
            user.setEmail(modelTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(modelTO.getPassword()));
            user.setStatus(modelTO.getStatus());


            int userId = this.userService.add(user);
            User user1 = this.userService.get(userId);


            res.put("id", user1.getId());
            res.put("username", user1.getUsername());
            res.put("firstName", user1.getFirstName());
            res.put("lastName", user1.getLastName());
            res.put("email", user1.getEmail());
            res.put("password", user1.getPassword());
            res.put("status", user1.getStatus());


            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (DuplicateKeyException ex) {
            res.put("error_message", "DUPLICATE ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "/api/user/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("user_id") String user_id) throws JSONException {
        JSONObject res = new JSONObject();
        try {

            System.err.print("get " + user_id);

            User user = this.userService.get(Integer.parseInt(user_id));

            res.put("user_id", user.getId());
            res.put("user_username", user.getUsername());
            res.put("user_firstName", user.getFirstName());
            res.put("user_lastName", user.getLastName());
            res.put("user_email", user.getEmail());
            res.put("user_password", user.getPassword());
            res.put("user_status", user.getStatus());

            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (Exception ex) {
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(path = "/api/user/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") int userId) throws JSONException {
        JSONObject res = new JSONObject();
        try {


            boolean user = this.userService.delete(userId);
            System.err.print("delete user_id -  " + userId);

            // if(user != null)

            int delete = this.accessTokenService.deleteByUserId(userId);
            System.err.print("delete accessToken - " + userId);

            int delete1 = this.refreshTokenService.deleteByUserId(userId);
            System.err.println("delete refreshToken - " + userId);


            res.put("message", "Deleted");
            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (Exception ex) {

            ex.printStackTrace();
            res.put("error_message", "this user not found ");
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping(path = "/api/user/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser1(@ModelAttribute User modelTO, @PathVariable("user_id") Integer userId) throws JSONException {
        JSONObject res = new JSONObject();
        try {

            if (userValidation.isValidUsername(modelTO.getUsername()) == false) {
                res.put("error_message", "Error Invalid Username ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidFirstName(modelTO.getFirstName()) == false) {
                res.put("error_message", "Error Invalid FirstName ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidLastName(modelTO.getLastName()) == false) {
                res.put("error_message", "Error Invalid LastName ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidEmail(modelTO.getEmail()) == false) {
                res.put("error_message", "Error Invalid Email");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidPassword(modelTO.getPassword()) == false) {
                res.put("error_message", "Error Invalid Password");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);

            }

            System.err.println("username - " + modelTO.getUsername());
            System.err.println("firstName - " + modelTO.getFirstName());
            System.err.println("lastName - " + modelTO.getLastName());
            System.err.println("email - " + modelTO.getEmail());
            System.err.println("password  - " + modelTO.getPassword());
            System.err.println("status - " + modelTO.getStatus());
            System.err.println(userId);

            User user = new User();

            user.setUsername(modelTO.getUsername());
            user.setFirstName(modelTO.getFirstName());
            user.setLastName(modelTO.getLastName());
            user.setEmail(modelTO.getEmail());
            user.setPassword(modelTO.getPassword());
            user.setStatus(modelTO.getStatus());
            user.setId(userId);

            int userId1 = this.userService.update(user);

            User user1 = this.userService.get(userId);

            res.put("username", user1.getUsername());
            res.put("firstName", user1.getFirstName());
            res.put("lastName", user1.getLastName());
            res.put("email", user1.getEmail());
            res.put("password", user1.getPassword());
            res.put("status", user1.getStatus());

            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (DuplicateKeyException ex) {
            res.put("error_message", "DUPLICATE ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        }

    }


}
