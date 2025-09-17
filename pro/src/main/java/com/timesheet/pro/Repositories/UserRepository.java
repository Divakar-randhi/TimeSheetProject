package com.timesheet.pro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timesheet.pro.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
