package com.timesheet.pro.Securtity;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.timesheet.pro.Services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

 
 

 
 
 
import java.io.IOException;
 
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        logger.debug("Incoming request URI: {}", request.getRequestURI());

        // Skip JWT validation for /auth/ endpoints
        if (request.getRequestURI().startsWith("/auth/")) {
            logger.info("Skipping JWT validation for auth endpoint: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = null;
        String username = null;

        // Extract JWT from Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.info("✅ JWT extracted. Username: {}", username);
            } catch (Exception e) {
                logger.error("❌ JWT extraction failed", e);
            }
        } else {
            logger.warn("❌ Authorization header missing or invalid format");
        }

        // Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            logger.debug("🔐 SecurityContextHolder now holds: {}", SecurityContextHolder.getContext().getAuthentication());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("✅ Authentication set for user: {}", username);
            } else {
                logger.warn("❌ Invalid JWT token for user: {}", username);
            }
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}