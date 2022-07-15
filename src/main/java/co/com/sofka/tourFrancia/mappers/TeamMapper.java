package co.com.sofka.tourFrancia.mappers;

import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.model.TeamDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TeamMapper {

  public Function<TeamDTO, Team> mapperToTeam(String id) {
    return updateTeam -> {
      var team = new Team();
      team.setId(id);
      team.setTemaName(updateTeam.getTemaName());
      team.setCountry(updateTeam.getCountry());
      return team;
    };
  }

  public Function<Team, TeamDTO> entityToTeamDTO() {
    return entity -> new TeamDTO(entity.getId(), entity.getTemaName(), entity.getCountry());
  }
}
