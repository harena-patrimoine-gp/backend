package com.harena.com.endpoint.rest.controller;

import com.harena.com.model.User;
import com.harena.com.service.RegisterService;
import com.harena.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.possession.Argent;

import java.time.LocalDate;

@RestController

public class UserController {
    @Autowired
    private  UserService userService;
    @Autowired
    private RegisterService registerService;
    @PostMapping("/register")
    public String registerUser(@RequestBody  com.harena.com.model.User user){
     return  registerService.registerUser(user);
    }
    @GetMapping("/user")
    public User getUserByEmail(@RequestParam String email){
        return  userService.findUserByEmail(email);
    }




}
