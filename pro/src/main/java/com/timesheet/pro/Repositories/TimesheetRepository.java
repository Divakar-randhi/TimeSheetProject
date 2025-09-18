package com.timesheet.pro.Repositories;
import com.timesheet.pro.Entities.Timesheet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {}

