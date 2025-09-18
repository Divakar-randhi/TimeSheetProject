package com.timesheet.pro.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.timesheet.pro.DTO.TimesheetDTO;
import com.timesheet.pro.Entities.Shift;
import com.timesheet.pro.Entities.TaskCategory;
import com.timesheet.pro.Entities.Timesheet;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Repositories.TaskCategoryRepository;
import com.timesheet.pro.Services.TimesheetService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/timesheets")
@RequiredArgsConstructor
public class TimesheetController {
    private final TimesheetService service;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TimesheetService timesheetService;
    

    @GetMapping("/GetAll")
    public List<Timesheet> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Timesheet getById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping("/Post")
    public ResponseEntity<Timesheet> create(@RequestBody TimesheetDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public Timesheet update(@PathVariable Integer id, @RequestBody TimesheetDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
     public TaskCategory getCategory(Integer id) {
        if (id == null) return null;
        return taskCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }
    private Shift getShift(Integer id) {
        if (id == null) return null;
        return shiftRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shift not found: " + id));
    }
    private User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
    }
    */
    @GetMapping("/category/{id}")
    public TaskCategory getTaskCategoryRepository(int id) {
       // return taskCategoryRepository.findBy(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
        return service.getCategory(id);
    }

    @GetMapping("/shift/{id}")
    public Shift getShift(int id) {
        return service.getShift(id);
    }  
     
    @GetMapping("/user/{id}")
    public User getUser(int id) {
        return service.getUser(id);
    }
   
}

