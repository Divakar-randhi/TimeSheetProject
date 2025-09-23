package com.timesheet.pro.controllers;

import com.timesheet.pro.DTO.UserCreateRequest;
import com.timesheet.pro.DTO.UserResponse;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Enums.EducationQualification;
import com.timesheet.pro.Enums.Gender;
import com.timesheet.pro.Enums.Relationship;
import com.timesheet.pro.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/upi/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ Create user
    @PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> createUser(
            @RequestParam String firstName,
            @RequestParam String middleName,
            @RequestParam String lastName,
            @RequestParam String contactNumber,
            @RequestParam(required = false) String skills,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String emergencyContactName,
            @RequestParam(required = false) String emergencyContactNumber,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String relationship,
            @RequestParam(required = false) String educationQualification,
            @RequestParam(required = false) String birthDate,
            @RequestPart(required = false) MultipartFile photo
    ) {
        UserCreateRequest req = new UserCreateRequest();
        req.setFirstName(firstName);
        req.setMiddleName(middleName);
        req.setLastName(lastName);
        req.setContactNumber(contactNumber);
        req.setSkills(skills);
        req.setAddress(address);
        req.setEmergencyContactName(emergencyContactName);
        req.setEmergencyContactNumber(emergencyContactNumber);
        req.setGender(gender != null ? Gender.valueOf(gender) : null);
        req.setRelationship(relationship != null ? Relationship.valueOf(relationship) : null);
        req.setEducationQualification(educationQualification != null ? EducationQualification.valueOf(educationQualification) : null);
        req.setBirthDate(birthDate != null ? LocalDate.parse(birthDate) : null);
        req.setPhoto(photo);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(req));
    }

    // ✅ Get one user
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userService.toResponse(user));
    }

    // ✅ Get all users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Update user
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer id,
            @RequestParam String firstName,
            @RequestParam String middleName,
            @RequestParam String lastName,
            @RequestParam String contactNumber,
            @RequestParam(required = false) String skills,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String emergencyContactName,
            @RequestParam(required = false) String emergencyContactNumber,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String relationship,
            @RequestParam(required = false) String educationQualification,
            @RequestParam(required = false) String birthDate,
            @RequestPart(required = false) MultipartFile photo
    ) {
        UserCreateRequest req = new UserCreateRequest();
        req.setFirstName(firstName);
        req.setMiddleName(middleName);
        req.setLastName(lastName);
        req.setContactNumber(contactNumber);
        req.setSkills(skills);
        req.setAddress(address);
        req.setEmergencyContactName(emergencyContactName);
        req.setEmergencyContactNumber(emergencyContactNumber);
        req.setGender(gender != null ? Gender.valueOf(gender) : null);
        req.setRelationship(relationship != null ? Relationship.valueOf(relationship) : null);
        req.setEducationQualification(educationQualification != null ? EducationQualification.valueOf(educationQualification) : null);
        req.setBirthDate(birthDate != null ? LocalDate.parse(birthDate) : null);
        req.setPhoto(photo);

        return ResponseEntity.ok(userService.updateUser(id, req));
    }

    // ✅ Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Upload photo
    @PutMapping(path = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@PathVariable Integer id, @RequestPart MultipartFile photo) {
        userService.uploadPhoto(id, photo);
        return ResponseEntity.ok().build();
    }

    // ✅ Get photo
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer id) {
        byte[] photo = userService.getPhoto(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("user-" + id + ".jpg")
                                .build().toString())
                .body(photo);
    }
}
