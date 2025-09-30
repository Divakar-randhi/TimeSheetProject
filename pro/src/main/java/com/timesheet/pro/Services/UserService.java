package com.timesheet.pro.Services;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timesheet.pro.DTO.UserCreateRequest;
import com.timesheet.pro.DTO.UserDTO;
import com.timesheet.pro.DTO.UserResponse;
import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Entities.Role;
import com.timesheet.pro.Entities.Team;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Exceptions.NotFoundException;
import com.timesheet.pro.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.timesheet.pro.Repositories.RoleRepository;
import com.timesheet.pro.Repositories.Teamrepo;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Added for Role fetching
    private final Teamrepo teamRepository; // Added for Team fetching

   // @Transactional
    // public UserResponse createUser(UserCreateRequest req) {
    //     User user = User.builder()
    //             .firstName(req.getFirstName())
    //             .middleName(req.getMiddleName())
    //             .lastName(req.getLastName())
    //             .contactNumber(req.getContactNumber())
    //             .birthDate(req.getBirthDate())
    //             .gender(req.getGender())
    //             .skills(req.getSkills())
    //             .address(req.getAddress())
    //             .emergencyContactName(req.getEmergencyContactName())
    //             .emergencyContactNumber(req.getEmergencyContactNumber())
    //             .relationship(req.getRelationship())
    //             .educationQualification(req.getEducationQualification())
    //             .build();

    //     User saved = userRepository.save(user);
    //     return toResponse(saved);
    // }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
               // .roleId(user.getroleId()) // Added for Role ID
                .roleId(user.getRole().getRoleId()) // Added for Role ID
                //.teamId(user.getTeam().getteamId()) // Added for Team ID
                .teamId(user.getTeam().getTeamid()) // Added for Team ID
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .skills(user.getSkills())
                .address(user.getAddress())
                .contactNumber(user.getContactNumber())
                .emergencyContactName(user.getEmergencyContactName())
                .emergencyContactNumber(user.getEmergencyContactNumber())
                .relationship(user.getRelationship())
                .educationQualification(user.getEducationQualification())
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

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

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public User create(UserDTO dto, AppUser appUser) {
        // Fetch Role entity
        Role role = null;
        if (dto.getRoleId() != null) {
            role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + dto.getRoleId()));
        }

        // Fetch Team entity
        Team team = null;
        if (dto.getTeamId() != null) {
            team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + dto.getTeamId()));
        }

        User user = User.builder()
                .role(role)
                .team(team)
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .skills(dto.getSkills())
                .address(dto.getAddress())
                .contactNumber(dto.getContactNumber())
                .emergencyContactName(dto.getEmergencyContactName())
                .emergencyContactNumber(dto.getEmergencyContactNumber())
                .relationship(dto.getRelationship())
                .educationQualification(dto.getEducationQualification())
                .appUser(appUser) // Set the AppUser for ownership
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}