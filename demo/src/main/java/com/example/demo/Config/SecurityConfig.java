package com.example.demo.Config;

import com.example.demo.JWT.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //  Dependencias necesarias para la autenticación
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                // se deshabilida para poder utilzar token
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                // declaro mis endpoints publicos
                                .requestMatchers("/auth/**").permitAll()
                                // solo ADMIN especificos
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                // solo user
                                .requestMatchers("/user/**").hasRole("USER")
                                // cualquier otro endpoint necesita pasar la validacion de auth
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManeger ->
                        sessionManeger
                                // se declara que no se utilizaran sesiones
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // se manda a validar
                .authenticationProvider(authProvider)
                // se aniade el filtro de auth
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
