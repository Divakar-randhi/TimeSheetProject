package com.timesheet.pro.Repositories;

import com.timesheet.pro.Entities.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Integer> {}


