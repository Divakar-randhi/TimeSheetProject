package com.timesheet.pro.Entities;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Table(name = "userdatiles")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = true, updatable = true)
    @JsonIgnore
    private Role role;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Column(name = "middle_name", nullable = false, length = 100)
    private String middleName;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    private LocalDate birthDate;

    // DB ENUM('Male','Female','Other') -> Store as String for simplicity
    @Column(columnDefinition = "ENUM('Male','Female','Other')")
    private String gender;

    @Column(length = 1000)
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank
    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_number", length = 15)
    private String emergencyContactNumber;

    @Column(columnDefinition = "ENUM('Father','Mother','Sister','Brother','Spouse')")
    private String relationship;

    // DB ENUM('B.Tech','Degree') -> contains dot; keep as String
    @Column(name = "education_qualification", columnDefinition = "ENUM('B.Tech','Degree')")
    private String educationQualification;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Timesheet> timesheets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserPosition> userPositions;
    
}

