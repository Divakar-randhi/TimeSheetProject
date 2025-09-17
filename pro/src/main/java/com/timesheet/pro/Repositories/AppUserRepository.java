package com.timesheet.pro.Repositories;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timesheet.pro.Entities.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    default Optional<AppUser> logAndFindByUsername(String username) {
    Logger logger = LoggerFactory.getLogger(UserRepository.class);
    logger.debug("🔎 Checking if username exists in DB: {}", username);
    return findByUsername(username);
}
}