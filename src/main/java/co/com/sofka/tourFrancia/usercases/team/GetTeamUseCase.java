package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetTeamUseCase implements Function<String, Mono<TeamDTO>> {

  private final TeamRepository teamRepository;
  private final CyclistRepository cyclistRepository;
  private final TeamMapper teamMapperr;
  private final CyclistMapper cyclistMapper;

  public GetTeamUseCase(
      TeamRepository teamRepository,
      CyclistRepository cyclistRepository,
      TeamMapper teamMapperr,
      CyclistMapper cyclistMapper) {
    this.teamRepository = teamRepository;
    this.cyclistRepository = cyclistRepository;
    this.teamMapperr = teamMapperr;
    this.cyclistMapper = cyclistMapper;
  }



  @Override
  public Mono<TeamDTO> apply(String id) {
    Objects.requireNonNull(id, "Id is required");
    return teamRepository
        .findById(id)
        .map(teamMapperr.entityToTeamDTO())
        .flatMap(mapTeamAggregate());
  }

  private Function<TeamDTO, Mono<TeamDTO>> mapTeamAggregate() {
    return teamDTO ->
        Mono.just(teamDTO)
            .zipWith(
                cyclistRepository
                    .findAllByTeamId(teamDTO.getId())
                    .map(cyclistMapper.cyclistToCyclistDTO())
                    .collectList(),
                (team, cyclists) -> {
                  team.setCyclists(cyclists);
                  return team;
                });
  }
}
