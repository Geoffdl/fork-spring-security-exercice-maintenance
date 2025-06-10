package com.example.demo.controllers;

import com.example.demo.models.UserApp;
import com.example.demo.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserAppService userAppService;

    @PostMapping("/create-user")
    public String createUser(@RequestBody UserApp userApp) throws Exception {
         userAppService.createUserApp(userApp, "ROLE_USER");
        return "user créé";
    }

    @GetMapping("/create-admin")
    public String createAdmin(@RequestBody UserApp userApp) throws Exception {
        userAppService.createUserApp(userApp, "ROLE_ADMIN");
        return "admin créé";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserApp userApp) throws Exception {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, userAppService.logUserApp(userApp).toString())
                .body("vous êtes login");
    }
}
