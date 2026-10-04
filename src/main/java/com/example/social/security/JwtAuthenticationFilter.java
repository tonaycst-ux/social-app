package com.example.social.security;

import com.example.social.models.User;
import com.example.social.repository.UserRepo;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepo userRepo;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserRepo userRepo
    ) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("REQUEST = " + request.getRequestURI());
        System.out.println("AUTH HEADER = " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                String username =
                        jwtService.extractUsername(token);

                System.out.println("JWT USERNAME = " + username);

                User user =
                        userRepo.findByUsername(username);

                if (user != null) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),
                                    null,
                                    Collections.emptyList()
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println("AUTHENTICATED = " + username);
                }

            } catch (Exception e) {

                System.out.println("JWT ERROR = " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}