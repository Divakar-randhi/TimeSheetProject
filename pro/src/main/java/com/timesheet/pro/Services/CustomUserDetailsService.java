package com.timesheet.pro.Services;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Repositories.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private AppUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("🔍 Loading user from DB: {}", username);

        AppUser user = userRepo.findByUsername(username)
            .orElseThrow(() -> {
                logger.warn("❌ User not found: {}", username);
                return new UsernameNotFoundException("User not found");
            });

        logger.debug("✅ User found: {}, Role: {}", user.getUsername(), user.getRole());

        // Ensure role is prefixed with "ROLE_" and handle null case
        String role = user.getRole();
        if (role == null) {
            role = "ROLE_USER"; // Default role if null
        } else if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        logger.info("🔐 Assigned role: {}", role);

        // Safety check for null values
        String usernameStr = (user.getUsername() != null) ? user.getUsername() : "";
        String passwordStr = (user.getPassword() != null) ? user.getPassword() : "";

        return new User(
            usernameStr,
            passwordStr,
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}