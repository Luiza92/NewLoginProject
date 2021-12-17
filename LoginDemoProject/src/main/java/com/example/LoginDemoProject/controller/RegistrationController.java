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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    User user = new User();

    UserValidation userValidation = new UserValidation();
    private RegistrationController request;


    @PostMapping(path = "/api/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registration(@ModelAttribute User modelTO) throws JSONException {
        try {


            if (userValidation.isValidUsername(modelTO.getUsername()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Username ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidFirstName(modelTO.getFirstName()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid FirstName ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidLastName(modelTO.getLastName()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid LastName ");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if (userValidation.isValidEmail(modelTO.getEmail()) == false) {
                JSONObject res = new JSONObject();
                res.put("error_message", "Error Invalid Email");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }

//            String password = this.user.getPassword();
//            String cPassword = this.user.getConfirmPassword();
//            if (passworp.equals(cPassword))



            if (userValidation.isValidPassword(modelTO.getPassword()) == false) {
                    JSONObject res = new JSONObject();
                    res.put("error_message", "Error Invalid Password");
                    return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
                }

            System.err.println("username - " + modelTO.getUsername());
            System.err.println("firstName - " + modelTO.getFirstName());
            System.err.println("lastName - " + modelTO.getLastName());
            System.err.println("email - " + modelTO.getEmail());
            System.err.println("password  - " + modelTO.getPassword());

            User user = new User();

            user.setUsername(modelTO.getUsername());
            user.setFirstName(modelTO.getFirstName());
            user.setLastName(modelTO.getLastName());
            user.setEmail(modelTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(modelTO.getPassword()));

            int userId = this.userService.add(user);
            User user1 = this.userService.get(userId);

            JSONObject res = new JSONObject();

            res.put("id", user1.getId());
            res.put("username", user1.getUsername());
            res.put("firstName", user1.getFirstName());
            res.put("lastName", user1.getLastName());
            res.put("email", user1.getEmail());
            res.put("password", user1.getPassword());

            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (DuplicateKeyException ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "DUPLICATE ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            JSONObject res = new JSONObject();
            res.put("error_message", "not found  " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.NOT_FOUND);
        }
    }
}






