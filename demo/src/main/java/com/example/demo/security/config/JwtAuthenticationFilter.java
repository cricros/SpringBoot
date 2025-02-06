package com.example.demo.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Jwttoken jwttoken;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(Jwttoken jwttoken, UserDetailsService userDetailsService){
        this.jwttoken = jwttoken;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
            // obtengo el header Authorization
            String authorizationHeader = request.getHeader("Authorization");
            // se hace la validacion
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.substring(7 );
            String username = jwttoken.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwttoken.validateToken(token, userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                }
            }
            filterChain.doFilter(request, response);

        }
    }
