package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestControllerAPI {

    // endpoint protegidos despues del login

    @PostMapping("/demo")
    public String demo(){
        return "welcome form secure endpoint";
    }
}
