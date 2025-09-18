package com.timesheet.pro.Entities;
import java.util.List;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@Entity
@Table(name = "Team")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer teamid;

  private String teamname;

 @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 @JsonIgnore
 private List<User> users;
 private String description;
  
}
