package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class GetAllTeamsUseCase implements Supplier<Flux<TeamDTO>> {
  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;

  public GetAllTeamsUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
    this.teamRepository = teamRepository;
    this.teamMapper = teamMapper;
  }

  @Override
  public Flux<TeamDTO> get() {
    return teamRepository.findAll().map(teamMapper.entityToTeamDTO());
  }
}
