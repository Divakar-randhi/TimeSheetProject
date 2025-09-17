package com.timesheet.pro.Services;
import com.timesheet.pro.DTO.TaskCategoryDTO;
import com.timesheet.pro.Entities.TaskCategory;
import com.timesheet.pro.Repositories.TaskCategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {
    private final TaskCategoryRepository repository;

    public List<TaskCategory> findAll() { return repository.findAll(); }

    public TaskCategory findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    public TaskCategory create(TaskCategoryDTO dto) {
        TaskCategory c = TaskCategory.builder()
                .categoryName(dto.getCategoryName())
                .description(dto.getDescription())
                .build();
        return repository.save(c);
    }

    public TaskCategory update(Integer id, TaskCategoryDTO dto) {
        TaskCategory c = findById(id);
        c.setCategoryName(dto.getCategoryName());
        c.setDescription(dto.getDescription());
        return repository.save(c);
    }

    public void delete(Integer id) { repository.deleteById(id); }
}
