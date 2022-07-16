package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class GetTeamByCountryUseCase implements Function<String, Flux<TeamDTO>> {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;

  public GetTeamByCountryUseCase(TeamRepository teamRepository, TeamMapper teamMapper) {
    this.teamRepository = teamRepository;
    this.teamMapper = teamMapper;
  }

  @Override
  public Flux<TeamDTO> apply(String country) {
    return teamRepository
        .findByCountry(StringUtils.capitalize(StringUtils.lowerCase(country)))
        .map(teamMapper.entityToTeamDTO());
  }
}
