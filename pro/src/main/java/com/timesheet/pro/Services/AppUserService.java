package com.timesheet.pro.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.timesheet.pro.DTO.RegisterRequest;
import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Repositories.AppUserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerUser(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        if (request.getUsername() == null || request.getPassword() == null || request.getRole() == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .email(request.getEmail())
                .build();

        appUserRepository.save(user);

        try {
            emailService.sendEmail(
                user.getEmail(),
                "Registration Confirmation",
                "Hi " + user.getUsername() + ",\n\nThank you for registering!"
            );
        } catch (Exception e) {
            return ResponseEntity.ok("User registered, but email failed: " + e.getMessage());
        }

        return ResponseEntity.ok("User registered successfully");
    }

//    public List<AppUser> getall () {
//        return appUserRepository.findAll();
       
//    }


}

