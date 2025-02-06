package com.example.demo.security.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // inyeccion de dependencias
    @Autowired
    public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                // se desactiva el csrf porque usaremos token en lugar sesiones
                .csrf(csrf -> csrf.disable())
                // sin sesiones
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // se declara una ruta publica
                        .requestMatchers("/", "/auth/login").permitAll()
                        // se declara una ruta protegida
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // cualquier peticion tiene que estar autenticada
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user")
                // {noop} nos indica que la contrasenia no esta encriptada
                .password("{noop}password")
                .roles("USER")
                .build();


        UserDetails admin = User.withUsername("admin")
                // {noop} nos indica que la contrasenia no esta encriptada
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();
        // los maneja en memoria pero solo temporal
        return new InMemoryUserDetailsManager(user,admin);
    }

}
