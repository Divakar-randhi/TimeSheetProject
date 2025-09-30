package com.timesheet.pro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timesheet.pro.Entities.AppUser;
import com.timesheet.pro.Entities.User;
import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {

 // List<User> findByFirstName(String firstName);

  Optional<User> findByAppUserId(Long appUserId);
boolean existsByAppUser(AppUser appUser); // ✅ For ownership check
    Optional<User> findByAppUser(AppUser appUser);
}

