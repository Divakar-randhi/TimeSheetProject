package com.timesheet.pro.Services;
import com.timesheet.pro.DTO.RegisterRequest;
import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Repositories.AppUserRepository;
import com.timesheet.pro.Services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;



@Service
public class PasswordService {
    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public PasswordService(AppUserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void createPasswordResetToken(String email) {
        AppUser user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepo.save(user);

        emailService.sendResetEmail(user.getEmail(), token);
    }

    public void resetPassword(String token, String newPassword) {
        AppUser user = userRepo.findByResetToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepo.save(user);
    }
}
