package com.timesheet.pro.Services;


import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Repositories.UserRepository;

import com.timesheet.pro.Exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.timesheet.pro.Exceptions.BadRequestException;
import com.timesheet.pro.Exceptions.NotFoundException;
import com.timesheet.pro.DTO.UserResponse;
import com.timesheet.pro.DTO.UserCreateRequest;
import com.timesheet.pro.Enums.EducationQualification;
import com.timesheet.pro.Enums.Gender;
import com.timesheet.pro.Enums.Relationship;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final long MAX_PHOTO_SIZE = 2 * 1024 * 1024; // 2 MB

    @Transactional
    public UserResponse createUser(UserCreateRequest req) {
        User user = User.builder()
                .firstName(req.getFirstName())
                .middleName(req.getMiddleName())
                .lastName(req.getLastName())
                .contactNumber(req.getContactNumber())
                .birthDate(req.getBirthDate())
                .gender(req.getGender())
                .skills(req.getSkills())
                .address(req.getAddress())
                .emergencyContactName(req.getEmergencyContactName())
                .emergencyContactNumber(req.getEmergencyContactNumber())
                .relationship(req.getRelationship())
                .educationQualification(req.getEducationQualification())
                .build();

        if (req.getPhoto() != null && !req.getPhoto().isEmpty()) {
            applyPhoto(user, req.getPhoto());
        }

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Transactional
    public void uploadPhoto(Integer userId, MultipartFile photo) {
        User user = getUserById(userId);
        applyPhoto(user, photo);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public byte[] getPhoto(Integer userId) {
        User user = getUserById(userId);
        if (user.getPhotoData() == null) {
            throw new NotFoundException("Photo not found for user " + userId);
        }
        return user.getPhotoData();
    }

    private void applyPhoto(User user, MultipartFile photo) {
        validatePhoto(photo);
        try {
            user.setPhotoData(photo.getBytes());
            user.setPhotoContentType(photo.getContentType());
            user.setPhotoFileName(photo.getOriginalFilename());
        } catch (IOException e) {
            throw new BadRequestException("Failed to read photo");
        }
    }

    private void validatePhoto(MultipartFile photo) {
        if (photo.getSize() > MAX_PHOTO_SIZE) {
            throw new BadRequestException("Photo too large. Max 2 MB allowed");
        }
        String type = photo.getContentType();
        if (type == null || !(type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/webp"))) {
            throw new BadRequestException("Only JPEG, PNG, WEBP formats allowed");
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public UserResponse toResponse(User user) {
    return UserResponse.builder()
            .userId(user.getUserId())
            .firstName(user.getFirstName())
            .middleName(user.getMiddleName())
            .lastName(user.getLastName())
            .birthDate(user.getBirthDate())
            //.gender(user.getGender() != null ? user.getGender().name() : null)
            .gender(user.getGender())
            .skills(user.getSkills())
            .address(user.getAddress())
            .contactNumber(user.getContactNumber())
            .emergencyContactName(user.getEmergencyContactName())
            .emergencyContactNumber(user.getEmergencyContactNumber())
            //.relationship(user.getRelationship() != null ? user.getRelationship().name() : null)
            .relationship(user.getRelationship())
            //.educationQualification(user.getEducationQualification() != null ? user.getEducationQualification().name() : null)
            .educationQualification(user.getEducationQualification())
            .hasPhoto(user.getPhotoData() != null)
            .photoDownloadUrl(user.getPhotoData() != null ? "/api/users/" + user.getUserId() + "/photo" : null)
            .build();
}

            // get all
            @Transactional(readOnly = true)
            public List<UserResponse> getAllUsers() {
            return userRepository.findAll().stream()
                 .map(this::toResponse)
                .toList();
            }

            // update
            @Transactional
            public UserResponse updateUser(Integer id, UserCreateRequest req) {
            User user = getUserById(id);

            user.setFirstName(req.getFirstName());
            user.setMiddleName(req.getMiddleName());
            user.setLastName(req.getLastName());
            user.setContactNumber(req.getContactNumber());
            user.setBirthDate(req.getBirthDate());
            user.setGender(req.getGender());
            user.setSkills(req.getSkills());
            user.setAddress(req.getAddress());
            user.setEmergencyContactName(req.getEmergencyContactName());
            user.setEmergencyContactNumber(req.getEmergencyContactNumber());
            user.setRelationship(req.getRelationship());
            user.setEducationQualification(req.getEducationQualification());

            if (req.getPhoto() != null && !req.getPhoto().isEmpty()) {
                applyPhoto(user, req.getPhoto());
            }

            return toResponse(userRepository.save(user));
        }

        // Delete User
            @Transactional
            public void deleteUser(Integer id) {
            User user = getUserById(id);
            userRepository.delete(user);
            }

}
