package com.example.LoginDemoProject.controller;
import com.example.LoginDemoProject.model.Approve;
import com.example.LoginDemoProject.model.User;
import com.example.LoginDemoProject.service.ApproveService;
import com.example.LoginDemoProject.service.UserService;
import com.example.LoginDemoProject.validation.UserValidation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class RegistrationController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    private ApproveService approveService;

    User user = new User();

    UserValidation userValidation = new UserValidation();


    @PostMapping(path = "/api/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registration(@ModelAttribute User modelTO) throws JSONException {
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

            if (!modelTO.getPassword().equals(modelTO.getConfirmPassword())) {
                res.put("error message - ", "password does not match");
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

            GenerateUUID generateUUID = new GenerateUUID();
            String randomId = generateUUID.Generate();
            System.err.println(randomId);

            Timestamp expiresTimestamp = new Timestamp(System.currentTimeMillis());

            Calendar c = Calendar.getInstance();
            c.setTime(expiresTimestamp);
            c.add(Calendar.MINUTE, (3));
            Timestamp TimeExpiresDateExDate = new Timestamp(c.getTimeInMillis());

            user.setUsername(modelTO.getUsername());
            user.setFirstName(modelTO.getFirstName());
            user.setLastName(modelTO.getLastName());
            user.setEmail(modelTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(modelTO.getPassword()));
            user.setStatus(1);

            int userId = this.userService.add(user);

            Approve approve = new Approve();

            approve.setUser_id(userId);
            approve.setRandomId(randomId);
            approve.setTimeExpires(TimeExpiresDateExDate);

            int approve1 = this.approveService.insert(approve);

            User user1 = this.userService.get(userId);


            String link = (String.format("http:/api/approve?user_id=%s&random_id=5s", userId, randomId));
            System.err.println(link);

            this.sendEmail(user.getEmail(), link, TimeExpiresDateExDate);


            res.put("id", user1.getId());
            res.put("username", user1.getUsername());
            res.put("firstName", user1.getFirstName());
            res.put("lastName", user1.getLastName());
            res.put("email", user1.getEmail());
            res.put("password", user1.getPassword());

            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (DuplicateKeyException ex) {
            res.put("error_message", "DUPLICATE ERROR MESSAGE " + ex.getMessage());
            return new ResponseEntity<>(res.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            res.put("error_message", "server error  " + ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    private JavaMailSender sender;

    private void sendEmail(String mail, String link, Timestamp date) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String soapXML = getResourceFileAsString("html/Conform.html");

        helper.setTo(mail);
        helper.setText(soapXML.replace("{link}", link).replace("{date}", date.toString()), true);

        helper.setSubject("Hello");
        sender.send(message);
    }

    @GetMapping(path = "/api/approve")
    public ResponseEntity<?> addApprove(@RequestParam("user_id") int user_id, @RequestParam("random_id") String random_id) throws Exception {

        return new ResponseEntity<>("You have successfully registration ", HttpStatus.OK);

    }

    public class GenerateUUID {

        public String Generate() {
            UUID uuid = UUID.randomUUID();
            return uuid.toString();
        }
    }

    public static String getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (String) reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = RegistrationController.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }


    @PostMapping(path = "/api/editProfile/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEditProfile(@RequestHeader Map<String, String> headers, @ModelAttribute User modelTO, @PathVariable("userId") Integer userId) throws JSONException {
        JSONObject res = new JSONObject();

        try {

            if (userId <= 0) {
                res.put("message", "invalid userId");
                return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            User user = this.userService.getByUserId(userId);

            if (user == null) {
                res.put("message", "invalid user");
                return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            String sql = ("select * from access_token where token = ?  and user_id = ?;");
            System.err.println("token: " + headers.get("authorization"));
            System.err.println("id: " + userId);
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, headers.get("authorization"), userId);

            if (result == null || result.size() == 0) {
                res.put("message", "this  access not used");
                return new ResponseEntity<>(res.toString(), HttpStatus.FORBIDDEN);
            }

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
            modelTO.setId(userId);

            int x = this.userService.update(modelTO);
            System.err.println(modelTO.getId());
            if (x== -1) {
                res.put("error_message", "User not found");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }
            res.put("message", "this user edit profile ");
            return new ResponseEntity<>(res.toString(), HttpStatus.OK);

        } catch (Exception ex) {
            res.put("error_message", "server error  " + ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping(path = "/api/changePassword/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changPassword(@RequestHeader Map<String, String> headers, @ModelAttribute User modelTO, @PathVariable("userId") Integer userId) throws JSONException {
        JSONObject res = new JSONObject();
        try {
            User user = this.userService.getByUserId(userId);
            if (user == null) {
                res.put("message", "invalid user");
                return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            String sql = ("select * from access_token where token = ?  and user_id = ?;");
            System.err.println("token: " + headers.get("authorization"));
            System.err.println("id: " + userId);
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, headers.get("authorization"), userId);

            if (result == null || result.size() == 0) {
                res.put("message", "this  access not used");
                return new ResponseEntity<>(res.toString(), HttpStatus.FORBIDDEN);
            }

            if (!modelTO.getPassword().equals(modelTO.getConfirmPassword())) {
                res.put("error message - ", "password does not match");
                return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(modelTO.getPassword()));

            String sql1 = ("update from users set password = ? where  id = ?");
            System.err.println("password  error");
            int result1 = jdbcTemplate.update(sql1, user.getPassword() , userId);

            if (result1 == 1) {
                res.put("message", "your password has been changed");
                return new ResponseEntity<>(res.toString(), HttpStatus.FORBIDDEN);
            }

        } catch (Exception ex) {
            res.put("error_message", "server error  " + ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(res.toString(), HttpStatus.BAD_REQUEST);

        }

        return null;
    }



}















