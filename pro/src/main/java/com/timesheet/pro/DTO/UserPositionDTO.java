package com.timesheet.pro.DTO;

import lombok.Data;

@Data
public class UserPositionDTO {
    private Integer userPositionId;
    private Integer userId;
    private Integer positionId;
    private String description;
}