package com.timesheet.pro.Services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.timesheet.pro.Repositories.Teamrepo;
import com.timesheet.pro.DTO.Teamdto;
import com.timesheet.pro.Entities.Team;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Teamservice {
    
  private final Teamrepo teamrepo;

  public List<Team> findAll() {
    return teamrepo.findAll();
  }
 
  public Team findById(Integer id) {
    return teamrepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found: " + id));
  }

  public Team create(Teamdto dto) {
    Team team = new Team();
    team.setTeamname(dto.getTeamName());
    team.setDescription(dto.getDescription());
    return teamrepo.save(team);
  }

  public Team update(Integer id, Teamdto dto) {
    Team team = findById(id);
    team.setTeamname(dto.getTeamName());
    team.setDescription(dto.getDescription());
    return teamrepo.save(team);
  }

  public void delete(Integer id) {
    teamrepo.deleteById(id);
  }

}
