package com.timesheet.pro.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.timesheet.pro.DTO.UserDTO;
import com.timesheet.pro.Entities.Role;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Repositories.RoleRepository;
import com.timesheet.pro.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    private Role getRole(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId));
    }

    public User create(UserDTO dto) {
        User user = User.builder()
                .role(getRole(dto.getRoleId()))
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
                .build();

        return userRepository.save(user);
    }

    public User update(Integer id, UserDTO dto) {
        User existing = findById(id);

        if (dto.getRoleId() != null) existing.setRole(getRole(dto.getRoleId()));
        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getMiddleName() != null) existing.setMiddleName(dto.getMiddleName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        existing.setBirthDate(dto.getBirthDate());
        if (dto.getGender() != null) existing.setGender(dto.getGender());
        existing.setSkills(dto.getSkills());
        existing.setAddress(dto.getAddress());
        if (dto.getContactNumber() != null) existing.setContactNumber(dto.getContactNumber());
        existing.setEmergencyContactName(dto.getEmergencyContactName());
        existing.setEmergencyContactNumber(dto.getEmergencyContactNumber());
        if (dto.getRelationship() != null) existing.setRelationship(dto.getRelationship());
        if (dto.getEducationQualification() != null) existing.setEducationQualification(dto.getEducationQualification());

        return userRepository.save(existing);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
