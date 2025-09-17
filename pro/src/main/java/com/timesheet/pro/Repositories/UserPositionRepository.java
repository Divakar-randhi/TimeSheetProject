package com.timesheet.pro.Repositories;
import com.timesheet.pro.Entities.UserPosition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPositionRepository extends JpaRepository<UserPosition, Integer> {}