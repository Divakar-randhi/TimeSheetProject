package com.timesheet.pro.DTO;

import java.time.LocalDate;

//import org.springframework.web.multipart.MultipartFile;

import com.timesheet.pro.Enums.EducationQualification;
import com.timesheet.pro.Enums.Gender;
import com.timesheet.pro.Enums.Relationship;
import lombok.Data;

@Data
public class UserCreateRequest {
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
  //  private MultipartFile photo;
}
