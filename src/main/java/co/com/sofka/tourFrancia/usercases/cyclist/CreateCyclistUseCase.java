package co.com.sofka.tourFrancia.usercases.cyclist;

import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.model.CyclistDTO;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateCyclistUseCase implements SaveCyclist {

  private final CyclistRepository cyclistRepository;
  private final CyclistMapper cyclistMapper;
  private final TeamRepository teamRepository;


  public CreateCyclistUseCase(
      CyclistRepository cyclistRepository,
      CyclistMapper cyclistMapper,
      TeamRepository teamRepository) {
    this.cyclistRepository = cyclistRepository;
    this.cyclistMapper = cyclistMapper;
    this.teamRepository = teamRepository;
  }

  @Override
  public Mono<String> apply(CyclistDTO cyclistDTO) {
    return guardar(cyclistDTO);
  }

  public Mono<String> guardar(CyclistDTO cyclistDTO) {
    return cyclistRepository
        .save(cyclistMapper.cyclistDTOToCyclist(null).apply(cyclistDTO))
        .flatMap(cyclist -> teamRepository.findById(cyclist.getTeamId()))
        .flatMap(
            team -> {
              return teamRepository.save(team);
            })
        .map(Team::getId)
        .switchIfEmpty(Mono.defer(() -> Mono.just("Empty")));
  }
}
