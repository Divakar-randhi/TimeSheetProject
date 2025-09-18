package com.timesheet.pro.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "position")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    @Column(name = "position_name", nullable = false, length = 100)
    private String positionName;

    @Column(name = "Roles_responsblities", columnDefinition = "TEXT")
    private String rolesResponsblities;

    @OneToMany(mappedBy = "position",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserPosition> userPositions;
}
