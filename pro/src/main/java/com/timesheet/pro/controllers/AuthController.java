package com.timesheet.pro.controllers;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timesheet.pro.DTO.AuthRequest;
import com.timesheet.pro.DTO.AuthResponse;
import com.timesheet.pro.DTO.RegisterRequest;
import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Entities.RefreshToken;
import com.timesheet.pro.Repositories.AppUserRepository;
import com.timesheet.pro.Repositories.RefreshTokenRepository;
import com.timesheet.pro.Securtity.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AppUserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        logger.info("📥 Register request received for username: {}", request.getUsername());

        if (request.getUsername() == null || request.getPassword() == null || request.getRole() == null) {
            logger.warn("❌ Missing fields in request: {}", request);
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("❌ Username already exists: {}", request.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }

        try {
            AppUser user = new AppUser();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            userRepo.save(user);
            logger.info("✅ User registered successfully: {}", user.getUsername());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("❌ Exception during registration", e);
            return ResponseEntity.status(500).body("Registration failed");
        }
    }
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        logger.info("📥 Login request received for username: {}", request.getUsername());

        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String accessToken = jwtUtil.generateToken(request.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());

            // Save refresh token to DB
            refreshTokenRepo.deleteByUsername(request.getUsername()); // optional cleanup
            RefreshToken tokenEntity = new RefreshToken();
            tokenEntity.setToken(refreshToken);
            tokenEntity.setUsername(request.getUsername());
            //tokenEntity.setExpiryDate(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)); // 7 days
            tokenEntity.setExpiryDate(new java.sql.Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)); // 7 days
            refreshTokenRepo.save(tokenEntity);

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
        } catch (Exception e) {
            logger.error("❌ Authentication failed for user: {}", request.getUsername(), e);
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        logger.info("🔄 Refresh token request received");

        Optional<RefreshToken> tokenOpt = refreshTokenRepo.findByToken(refreshToken);
        if (tokenOpt.isEmpty()) {
            logger.warn("❌ Refresh token not found");
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        RefreshToken tokenEntity = tokenOpt.get();
        if (tokenEntity.getExpiryDate().before(new Date(System.currentTimeMillis()))) {
            logger.warn("❌ Refresh token expired for user: {}", tokenEntity.getUsername());
            return ResponseEntity.status(401).body("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateToken(tokenEntity.getUsername());
        logger.info("✅ New access token generated for user: {}", tokenEntity.getUsername());

        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
    }
}
