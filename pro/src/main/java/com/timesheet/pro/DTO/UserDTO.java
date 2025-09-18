package com.timesheet.pro.DTO;

import java.time.LocalDate;

import com.timesheet.pro.Enums.Gender;
import com.timesheet.pro.Enums.Relationship;
import com.timesheet.pro.Enums.EducationQualification;

import lombok.Data;

@Data
public class UserDTO {
    private Integer roleId;
    private Integer teamId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String skills;
    private String address;
    private String contactNumber;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private Relationship relationship;
    private EducationQualification educationQualification;
}