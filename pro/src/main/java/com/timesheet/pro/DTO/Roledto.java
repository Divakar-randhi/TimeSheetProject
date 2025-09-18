package com.timesheet.pro.DTO;

import lombok.Data;

@Data
public class Roledto {
    
    private String roleName;
    private String description;

    public String getRoleName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}