package com.timesheet.pro.Services;

import com.timesheet.pro.DTO.ShiftDTO;
import com.timesheet.pro.Entities.Shift;
import com.timesheet.pro.Repositories.ShiftRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;

    private static final List<String> ALLOWED = List.of("Morning","evening","late-night","us-holiday","Indian_holiday");

    private void validate(ShiftDTO dto) {
        if (dto.getShiftName() != null && !ALLOWED.contains(dto.getShiftName()))
            throw new IllegalArgumentException("Invalid shift_name");
    }

    public List<Shift> findAll() { return shiftRepository.findAll(); }

    public Shift findById(Integer id) {
        return shiftRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shift not found: " + id));
    }

    public Shift create(ShiftDTO dto) {
        validate(dto);
        Shift s = Shift.builder()
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .shiftName(dto.getShiftName())
                .discription(dto.getDiscription())
                .build();
        return shiftRepository.save(s);
    }

    public Shift update(Integer id, ShiftDTO dto) {
        validate(dto);
        Shift s = findById(id);
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        s.setShiftName(dto.getShiftName());
        s.setDiscription(dto.getDiscription());
        return shiftRepository.save(s);
    }

    public void delete(Integer id) { shiftRepository.deleteById(id); }
}
