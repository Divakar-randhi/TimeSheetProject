package com.timesheet.pro.Repositories;
import com.timesheet.pro.Entities.Position;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {}

