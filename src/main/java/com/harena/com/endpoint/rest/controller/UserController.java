package com.harena.com.endpoint.rest.controller;

import com.harena.com.model.User;
import com.harena.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    @Autowired
    private  UserService userService;
    @PostMapping("/register")
    public User registerUser(@RequestBody  com.harena.com.model.User user){
     return  userService.registerUser(user);
    }
    @GetMapping("/user")
    public User getUserByEmail(@RequestParam String email){
        return  userService.findUserByEmail(email);
    }

}
