package com.timesheet.pro.Services;

import com.timesheet.pro.DTO.UserPositionDTO;
import com.timesheet.pro.Entities.Position;
import com.timesheet.pro.Entities.User;
import com.timesheet.pro.Entities.UserPosition;
import com.timesheet.pro.Repositories.PositionRepository;
import com.timesheet.pro.Repositories.UserPositionRepository;
import com.timesheet.pro.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPositionService {
    private final UserPositionRepository repository;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    private User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
    private Position getPosition(Integer id) {
        if (id == null) return null;
        return positionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Position not found: " + id));
    }

    public List<UserPosition> findAll() { return repository.findAll(); }

    public UserPosition findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("UserPosition not found: " + id));
    }

    public UserPosition create(UserPositionDTO dto) {
        UserPosition up = UserPosition.builder()
                .user(getUser(dto.getUserId()))
                .position(getPosition(dto.getPositionId()))
                .description(dto.getDescription())
                .build();
        return repository.save(up);
    }

    public UserPosition update(Integer id, UserPositionDTO dto) {
        UserPosition up = findById(id);
        if (dto.getUserId() != null) up.setUser(getUser(dto.getUserId()));
        up.setPosition(getPosition(dto.getPositionId()));
        up.setDescription(dto.getDescription());
        return repository.save(up);
    }

    public void delete(Integer id) { repository.deleteById(id); }
}

