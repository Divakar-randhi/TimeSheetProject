package com.timesheet.pro.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;

    public Object getUsername() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
