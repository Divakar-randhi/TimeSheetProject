package com.timesheet.pro.Entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "timesheet")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timesheetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = true, updatable = true)
    @JsonIgnore
    private TaskCategory category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shift_id", insertable = true, updatable = true)
    @JsonIgnore
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = true, updatable = true)
    @JsonIgnore
    private User user;

    private LocalDate workDate;

    // hours_worked TIME
    private LocalTime hoursWorked;
    @Column(columnDefinition = "TEXT")
    private String details;
}
