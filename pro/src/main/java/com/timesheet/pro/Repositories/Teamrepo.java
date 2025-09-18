package com.timesheet.pro.Repositories;
import com.timesheet.pro.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface Teamrepo extends JpaRepository<Team, Integer> {
  
}