package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateTeamUseCase implements SaveTeam {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;

  public UpdateTeamUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
    this.teamRepository = teamRepository;
    this.teamMapper = teamMapper;
  }

  @Override
  public Mono<String> apply(TeamDTO updateTeam) {
    Objects.requireNonNull(updateTeam.getId(), "El id del equipo no puede ser nulo");
    return teamRepository
        .save(teamMapper.mapperToTeam(updateTeam.getId()).apply(updateTeam))
        .map(Team::getId);
  }
}
