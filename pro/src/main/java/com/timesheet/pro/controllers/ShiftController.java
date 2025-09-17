package com.timesheet.pro.controllers;
import com.timesheet.pro.DTO.ShiftDTO;
import com.timesheet.pro.Entities.Shift;
import com.timesheet.pro.Services.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
public class ShiftController {
    private final ShiftService shiftService;

    @GetMapping("/GetAll")
    public List<Shift> getAll() { return shiftService.findAll(); }

    @GetMapping("/{id}")
    public Shift getById(@PathVariable Integer id) { return shiftService.findById(id); }

    @PostMapping("/Post")
    public ResponseEntity<Shift> create(@RequestBody ShiftDTO dto) {
        return ResponseEntity.ok(shiftService.create(dto));
    }

    @PutMapping("/{id}")
    public Shift update(@PathVariable Integer id, @RequestBody ShiftDTO dto) {
        return shiftService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        shiftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

