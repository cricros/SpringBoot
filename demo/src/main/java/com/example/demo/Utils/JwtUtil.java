package com.example.demo.Utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtUtil {
    // recupero los valores del aplication.properties
    @Value("${jtw.secret}")
    private String secretKey;

    // recupero los valores del aplication.properties
    @Value("${jtw.expiration}")
    private long lifeTime;
}
