package com.timesheet.pro.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesheet.pro.Enums.EducationQualification;
import com.timesheet.pro.Enums.Gender;
import com.timesheet.pro.Enums.Relationship;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "userdatiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "middle_name", nullable = false, length = 100)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(length = 1000)
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_number", length = 15)
    private String emergencyContactNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Relationship relationship;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_qualification", length = 15)
    private EducationQualification educationQualification;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Timesheet> timesheets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserPosition> userPositions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = true, updatable = true)
    @JsonIgnore
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", unique = true)
    @JsonIgnore
    private AppUser appUser;

//     @Lob
// @Basic(fetch = FetchType.LAZY)
// @Column(name = "photo_data")
// private byte[] photoData;

// @Column(name = "photo_content_type", length = 100)
// private String photoContentType;

// @Column(name = "photo_filename", length = 255)
// private String photoFileName;

// @Column(name = "created_at", nullable = false, updatable = false)
// private LocalDateTime createdAt;

// @Column(name = "updated_at")
// private LocalDateTime updatedAt;

// @PrePersist
// protected void onCreate() {
//     this.createdAt = LocalDateTime.now();
//     this.updatedAt = LocalDateTime.now();
// }

// @PreUpdate
// protected void onUpdate() {
//     this.updatedAt = LocalDateTime.now();
// }





}
