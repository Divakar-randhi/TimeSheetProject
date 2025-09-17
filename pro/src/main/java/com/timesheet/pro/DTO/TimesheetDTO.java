package com.timesheet.pro.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimesheetDTO {
    private Integer timesheetId;
    private Integer categoryId;
    private Integer shiftId;
    private Integer userId;
    private LocalDate workDate;
    private LocalTime hoursWorked;
    private String details;
}
