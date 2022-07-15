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
    return guarda(cyclistDTO);
  }

  public Mono<String> guarda(CyclistDTO cyclistDTO) {
    return cyclistRepository
        .save(cyclistMapper.cyclistDTOToCyclist(null).apply(cyclistDTO))
        .flatMap(risk -> teamRepository.findById(risk.getTeamId()))
        .flatMap(
            project -> {
              return teamRepository.save(project);
            })
        .map(Team::getId)
        .switchIfEmpty(Mono.defer(() -> Mono.just("Empty")));
  }
}
