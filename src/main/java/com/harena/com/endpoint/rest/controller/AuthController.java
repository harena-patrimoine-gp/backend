package com.harena.com.endpoint.rest.controller;

import com.harena.com.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @PostMapping("/token")
    public String generateToken(Authentication authentication){
      return   tokenService.generateToken(authentication);
    }
}
