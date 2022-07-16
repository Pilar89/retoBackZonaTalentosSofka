package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateTeamUseCase implements SaveTeam {
  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;

  public CreateTeamUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
    this.teamRepository = teamRepository;
    this.teamMapper = teamMapper;
  }

  @Override
  public Mono<String> apply(TeamDTO teamDTO) {
    return guardar(teamDTO);
  }

  public Mono<String> guardar(TeamDTO newTeam) {
    String country = newTeam.getCountry();
   newTeam.setCountry(StringUtils.capitalize(StringUtils.lowerCase(country) ));
    return teamRepository.save(teamMapper.mapperToTeam(null).apply(newTeam)).map(Team::getId);
  }
}
