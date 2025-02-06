package com.example.demo.security.config;

import com.example.demo.Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class Jwttoken {

    // se inyecta la clase JwtUtil para obtener los valores de mi applicacion.properties
    private JwtUtil jwtUtil;

    @Autowired
    public Jwttoken(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    // Genera un token basado en el nombre del usuario
    public String generateToken(String username){
        return Jwts.builder()
                // seteo el username
                .setSubject(username)
                // fecha de emision
                .setIssuedAt(new Date())
                // se le suma a una fecha el tiempo de vida del token
                .setExpiration(new Date(System.currentTimeMillis()+jwtUtil.getLifeTime()))
                // se realiza el cifrado con la secret key
                .signWith(SignatureAlgorithm.HS512, jwtUtil.getSecretKey())
                .compact();
    }

    // extrae el nombre de usuario desde del token
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }


    // valida que el token es correcto y no ha expirado
    public boolean validateToken(String token, String username){
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // 🔹 Metodo para obtener los claims del token
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtUtil.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    // valida si el token expiro
    private boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }


}
