package com.timesheet.pro.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.timesheet.pro.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  public Role findByRoleName(String roleName);

  // @Query("SELECT r FROM Role r WHERE r.roleName = :roleName")
  // public Role findByRoleName(String roleName);

}

