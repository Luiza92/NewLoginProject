package com.example.LoginDemoProject.controller;

import com.example.LoginDemoProject.model.User;
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
    User user = new User();
    UserValidation userValidation = new UserValidation();


    @PostMapping(path = "/api/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser1(@ModelAttribute User modelTO) throws JSONException {
        try {

            if (userValidation.isValidUsername(modelTO.getUsername()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Username ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidSurname(modelTO.getSurname()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Surname ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidEmail(modelTO.getEmail()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Email");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidPassword(modelTO.getPassword()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Password");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);

            }

            System.err.println("username - " + modelTO.getUsername());
            System.err.println("surname - " + modelTO.getSurname());
            System.err.println("email - " + modelTO.getEmail());
            System.err.println("password  - " + modelTO.getPassword());


            User user = new User();

            user.setUsername(modelTO.getUsername());
            user.setSurname(modelTO.getSurname());
            user.setEmail(modelTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(modelTO.getPassword()));


            int userId = this.userService.add(user);
            User user1 = this.userService.get(userId);

            JSONObject res = new JSONObject();

            res.put("id", user1.getId());
            res.put("username", user1.getUsername());
            res.put("surname", user1.getSurname());
            res.put("email", user1.getEmail());
            res.put("password", user1.getPassword());


            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (DuplicateKeyException ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "DUPLICATE ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "/api/user/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("user_id") String user_id) throws JSONException {
        System.out.printf("4k44k4k4k");
        try {

            System.err.print("get " + user_id);

            JSONObject res = new JSONObject();
            User user = this.userService.get(Integer.parseInt(user_id));

            res.put("user_id", user.getId());
            res.put("user_username", user.getUsername());
            res.put("user_surname", user.getSurname());
            res.put("user_email", user.getEmail());
            res.put("user_password", user.getPassword());

            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (Exception ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(path = "/api/user/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> geteUser1(@PathVariable("user_id") int user_id) throws JSONException {
        try {

            JSONObject res = new JSONObject();

            System.err.print("delete " + user_id);
            boolean user = this.userService.delete(user_id);

            if (user) {
                res.put("message", "Deleted");
                return new ResponseEntity<>(res.toString(), HttpStatus.OK);
            }
            res.put("error_message", "this user not found ");
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {

            JSONObject res = new JSONObject();
            ex.printStackTrace();

            res.put("error_message", " ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping(path = "/api/user/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser1(@ModelAttribute User modelTO, @PathVariable("user_id") Integer user_id) throws JSONException {

        try {

            if (userValidation.isValidUsername(modelTO.getUsername()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Username ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidSurname(modelTO.getSurname()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Surname ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidEmail(modelTO.getEmail()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Email");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidPassword(modelTO.getPassword()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Password");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);

            }

            System.err.println("username - " + modelTO.getUsername());
            System.err.println("surname - " + modelTO.getSurname());
            System.err.println("email - " + modelTO.getEmail());
            System.err.println("password  - " + modelTO.getPassword());
            System.err.println(user_id);

            User user = new User();

            user.setUsername(modelTO.getUsername());
            user.setSurname(modelTO.getSurname());
            user.setEmail(modelTO.getEmail());
            user.setPassword(modelTO.getPassword());
            user.setId(user_id);

            int userId = this.userService.update(user);
            JSONObject res = new JSONObject();

            User user1 = this.userService.get(user_id);

            res.put("username", user1.getUsername());
            res.put("surname", user1.getSurname());
            res.put("email", user1.getEmail());
            res.put("password", user1.getPassword());


            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (DuplicateKeyException ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "DUPLICATE ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "this user not found " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        }

    }


}
