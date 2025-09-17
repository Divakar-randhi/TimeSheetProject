package com.timesheet.pro.controllers;

import com.timesheet.pro.DTO.TaskCategoryDTO;
import com.timesheet.pro.Entities.TaskCategory;
import com.timesheet.pro.Services.TaskCategoryService;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/task-categories")
@RequiredArgsConstructor
public class TaskCategoryController {
    private final TaskCategoryService service;

    @GetMapping("/GetAll")
    public List<TaskCategory> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public TaskCategory getById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping("/Post")
    public ResponseEntity<TaskCategory> create(@RequestBody TaskCategoryDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public TaskCategory update(@PathVariable Integer id, @RequestBody TaskCategoryDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

