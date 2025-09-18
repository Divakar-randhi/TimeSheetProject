package com.timesheet.pro.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.timesheet.pro.DTO.Teamdto;
import com.timesheet.pro.DTO.Teamdto;
import com.timesheet.pro.Entities.Team;
import com.timesheet.pro.Services.Teamservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class Teamcontroller {

    private final Teamservice teamservice;

    // 🔍 Get all teams
    @GetMapping("/GetAll")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamservice.findAll());
    }

    // 🔍 Get team by ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id) {
        return ResponseEntity.ok(teamservice.findById(id));
    }

    // ✅ Create new team
    @PostMapping("/post")
    public ResponseEntity<Team> createTeam(@RequestBody Teamdto dto) {
        return ResponseEntity.ok(teamservice.create(dto));
    }

    // ✅ Update team
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Integer id, @RequestBody Teamdto dto) {
        return ResponseEntity.ok(teamservice.update(id, dto));
    }

    // 🗑️ Delete team (optional)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        teamservice.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
