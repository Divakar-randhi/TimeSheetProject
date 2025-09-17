package com.timesheet.pro.controllers;
import com.timesheet.pro.DTO.PositionDTO;
import com.timesheet.pro.Entities.Position;
import com.timesheet.pro.Services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService service;

    @GetMapping("/GetAll")
    public List<Position> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Position getById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping("/Post")
    public ResponseEntity<Position> create(@RequestBody PositionDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public Position update(@PathVariable Integer id, @RequestBody PositionDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

