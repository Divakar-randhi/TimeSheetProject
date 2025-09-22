package com.timesheet.pro.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.timesheet.pro.DTO.Roledto;
import com.timesheet.pro.Entities.Role;
import com.timesheet.pro.Repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll() { return roleRepository.findAll(); }

    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
    }

    public Role create(Roledto dto) {
        Role role = Role.builder()
                .roleName(dto.getRoleName())
                .description(dto.getDescription())
                .build();
        return roleRepository.save(role);
    }

    public Role update(Integer id, Roledto dto) {
        Role existing = findById(id);
        existing.setRoleName(dto.getRoleName());
        existing.setDescription(dto.getDescription());
        return roleRepository.save(existing);
    }

    public void delete(Integer id) { roleRepository.deleteById(id); 
    }


    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
    
    
    
}

