package com.timesheet.pro.Repositories;

import com.timesheet.pro.Entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {}

