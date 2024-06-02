package com.project.demo.controllers;

import com.project.demo.entities.User;
import com.project.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userServices;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.ok(userServices.register(user));
    }
    @PostMapping("/login")
    public ResponseEntity<User>login(@RequestBody Map<String,String> credentials) {
        return ResponseEntity.ok(userServices.login(
                credentials.get("username"),
                credentials.get("password")));
    }
}
