package com.timesheet.pro.Services;

import com.timesheet.pro.DTO.TimesheetDTO;
import com.timesheet.pro.Entities.Timesheet;
import com.timesheet.pro.Repositories.TimesheetRepository;
import com.timesheet.pro.Repositories.TaskCategoryRepository;
import com.timesheet.pro.Repositories.ShiftRepository;
import com.timesheet.pro.Repositories.UserRepository;
import com.timesheet.pro.excelexporter.TimesheetExcelExporter;
import com.timesheet.pro.Entities.Shift;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Entities.TaskCategory;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimesheetService {
    private final TimesheetRepository timesheetRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final ShiftRepository shiftRepository;
    private final UserRepository userRepository;

    @Autowired
    private  TimesheetExcelExporter excelExporter;

    public TaskCategory getCategory(Integer id) {
        if (id == null) return null;
        return taskCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }
    public Shift getShift(Integer id) {
        if (id == null) return null;
        return shiftRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shift not found: " + id));
    }
    public User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    public List<Timesheet> findAll() { return timesheetRepository.findAll(); }

    public Timesheet findById(Integer id) {
        return timesheetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Timesheet not found: " + id));
    }

    public Timesheet create(TimesheetDTO dto) {
        Timesheet t = Timesheet.builder()
                .category(getCategory(dto.getCategoryId()))
                .shift(getShift(dto.getShiftId()))
                .user(getUser(dto.getUserId()))
                .workDate(dto.getWorkDate())
                .hoursWorked(dto.getHoursWorked())
                .details(dto.getDetails())
                .build();
        return timesheetRepository.save(t);
    }

    public Timesheet update(Integer id, TimesheetDTO dto) {
        Timesheet t = findById(id);
        t.setCategory(getCategory(dto.getCategoryId()));
        t.setShift(getShift(dto.getShiftId()));
        if (dto.getUserId() != null) t.setUser(getUser(dto.getUserId()));
        t.setWorkDate(dto.getWorkDate());
        t.setHoursWorked(dto.getHoursWorked());
        t.setDetails(dto.getDetails());
        return timesheetRepository.save(t);
    }

    public void delete(Integer id) { timesheetRepository.deleteById(id); }

    public List<TimesheetDTO> getAllTimesheets() {
        return timesheetRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void exportAllToExcel() {
        List<Timesheet> timesheets = timesheetRepository.findAll();
        excelExporter.exportAllTimesheets(timesheets);
    }

    private TimesheetDTO toDTO(Timesheet t) {
        TimesheetDTO dto = new TimesheetDTO();
        dto.setTimesheetId(t.getTimesheetId());
        dto.setCategoryId(t.getCategory() != null ? t.getCategory().getCategoryId() : null);
        dto.setShiftId(t.getShift() != null ? t.getShift().getShiftId() : null);
        dto.setUserId(t.getUser() != null ? t.getUser().getUserId() : null);
        dto.setWorkDate(t.getWorkDate());
        dto.setHoursWorked(t.getHoursWorked());
        dto.setDetails(t.getDetails());
        return dto;
    }
}

