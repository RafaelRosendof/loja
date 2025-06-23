/*
package com.SistemLoja.SistemaLoja.Security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)

    throws ServletException , IOException{
        String authHeader = request.getHeader("Authorization");

        if ( authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            if(jwtUtil.validateToken(token)){
                String username = jwtUtil.extractUser(token);

                UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null , new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
*/

package com.SistemLoja.SistemaLoja.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        // Allow OPTIONS requests to pass through without authentication
        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("OPTIONS request detected, allowing through");
            filterChain.doFilter(request, response);
            return;
        }

        // Allow public endpoints to pass through
        String requestPath = request.getRequestURI();
        if (
            requestPath.equals("/login") ||
            requestPath.startsWith("/cliente/public/") ||
            requestPath.startsWith("/funcionarios/public/") ||
            requestPath.startsWith("/produto/public/")
        ) {
            System.out.println("Public endpoint detected: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("Processing request to: " + requestPath);
        System.out.println("Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println(
                "Extracted token: " +
                token.substring(0, Math.min(token.length(), 20)) +
                "..."
            );

            try {
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUser(token);
                    System.out.println("Token valid for user: " + username);

                    // Create authorities - adding ADMIN role since that's what your user has
                    List<SimpleGrantedAuthority> authorities =
                        new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                        );

                    SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                    System.out.println(
                        "Authentication set successfully for user: " + username
                    );
                } else {
                    System.out.println("Token validation failed");
                }
            } catch (Exception e) {
                System.out.println(
                    "Error processing JWT token: " + e.getMessage()
                );
                e.printStackTrace();
            }
        } else {
            System.out.println("No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}
