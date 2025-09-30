package com.timesheet.pro.controllers;

import com.timesheet.pro.DTO.UserCreateRequest;
import com.timesheet.pro.DTO.UserDTO;
import com.timesheet.pro.DTO.UserInfoDTO;
import com.timesheet.pro.DTO.UserResponse;
import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Enums.EducationQualification;
import com.timesheet.pro.Enums.Gender;
import com.timesheet.pro.Enums.Relationship;
import com.timesheet.pro.Repositories.AppUserRepository;
import com.timesheet.pro.Repositories.UserRepository;
import com.timesheet.pro.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AppUserRepository appUserRepo;
    private final UserRepository userRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/postuser/{id}")
    public ResponseEntity<?> createProfile(@PathVariable Long id, @RequestBody UserDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("🔐 Token username: {}", username);

        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("❌ AppUser not found for username: {}", username);
                    return new IllegalArgumentException("User not found");
                });

        logger.info("✅ AppUser ID from DB: {}", appUser.getId());
        logger.info("✅ ID from URL: {}", id);

        if (!appUser.getId().equals(id)) {
        //    logger.error("❌ ID mismatch — cannot post for another user");
            return ResponseEntity.status(403).body("You can only post your own profile");
        }

        logger.info("✅ Email in DB: {}", appUser.getEmail());
        logger.info("✅ Email in payload: {}", dto.getEmail());

        if (!appUser.getEmail().equalsIgnoreCase(dto.getEmail())) {
        //    logger.error("❌ Email mismatch — payload email doesn't match registered email");
            return ResponseEntity.status(403).body("Email mismatch — profile not allowed");
        }

        boolean profileExists = userRepo.existsByAppUser(appUser);
        logger.info("🔍 Profile already exists: {}", profileExists);

        if (profileExists) {
       //     logger.error("❌ Profile already exists for this user");
            return ResponseEntity.status(403).body("Profile already exists for this user");
        }

        User user = userService.create(dto, appUser);
        logger.info("✅ Profile created successfully for: {}", username);

        return ResponseEntity.ok(userService.toResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userService.toResponse(user));
    }

  @GetMapping("/GetAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Authenticated username: {}", username);
        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found for username: {}", username);
                    return new IllegalArgumentException("User not found");
                });
        logger.info("User ID: {}", appUser.getId());

        User userProfile = userRepo.findByAppUserId(appUser.getId()).orElse(null);
        if (userProfile != null) {
            UserInfoDTO userInfo = new UserInfoDTO(appUser.getId(), appUser.getEmail(), userProfile);
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.ok(new UserInfoDTO(appUser.getId(), appUser.getEmail()));
        }
    }
}