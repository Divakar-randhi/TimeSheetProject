package com.timesheet.pro.Services;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.timesheet.pro.Repositories.AppUserRepository;

import com.timesheet.pro.Entities.AppUser;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendResetEmail(String to, String token) {
        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;
        String body = "Click the link to reset your password: " + resetUrl;
        sendEmail(to, "Password Reset", body);
    }
}
