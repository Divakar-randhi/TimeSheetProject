package com.timesheet.pro.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timesheet.pro.DTO.Roledto;
import com.timesheet.pro.Entities.Role;
import com.timesheet.pro.Services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @GetMapping("/GetAll")
    @Operation(summary = "Get all Roles", description = "Retrieve all Roles details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "roles retrieved successfully."),
			@ApiResponse(responseCode = "401", description = "forebidden"),
			@ApiResponse(responseCode = "404", description = "role not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
    public List<Role> getAll() { return roleService.findAll(); }

    @GetMapping("/{id}")
    public Role getById(@PathVariable Integer id) { return roleService.findById(id); }

     @PostMapping("/postrole")
    public ResponseEntity<Role> create(@RequestBody Roledto dto) {
    log.info("Received Role DTO: {}", dto); //  Log the incoming DTO
    Role role = roleService.create(dto);    // Create the Role
    log.info("Created Role Entity: {}", role); // Log the created Role
    return ResponseEntity.ok(role);
}

    @PutMapping("/{id}")
    public Role update(@PathVariable Integer id, @RequestBody Roledto dto) { return roleService.update(id, dto); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {  roleService.delete(id); }

    @GetMapping("/getrole/{rolename}")
    public void getRole(@PathVariable String rolename) {  roleService.findByRoleName(rolename); }
}

