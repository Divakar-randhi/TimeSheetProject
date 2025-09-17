package com.timesheet.pro.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.timesheet.pro.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {}
