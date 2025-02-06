package com.example.demo.controller;

import com.example.demo.security.config.Jwttoken;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Jwttoken jwttoken;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, Jwttoken jwttoken, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwttoken = jwttoken;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwttoken.generateToken(username);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}
