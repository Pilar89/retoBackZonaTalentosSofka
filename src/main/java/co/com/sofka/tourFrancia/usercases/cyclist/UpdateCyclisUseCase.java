package co.com.sofka.tourFrancia.usercases.cyclist;

import co.com.sofka.tourFrancia.collections.Cyclist;
import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.model.CyclistDTO;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.usercases.team.GetTeamUseCase;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@Validated
public class UpdateCyclisUseCase implements SaveCyclist {

  private final CyclistRepository cyclistRepository;
  private final CyclistMapper cyclistMapper;
  private final GetTeamUseCase getTeamUseCase;

  public UpdateCyclisUseCase(
      CyclistRepository cyclistRepository,
      CyclistMapper cyclistMapper,
      GetTeamUseCase getTeamUseCase) {
    this.cyclistRepository = cyclistRepository;
    this.cyclistMapper = cyclistMapper;
    this.getTeamUseCase = getTeamUseCase;
  }

  @Override
  public Mono<String> apply(CyclistDTO updateCyclistDTO) {
    Objects.requireNonNull(updateCyclistDTO.getId(), "El id del proyecto no puede ser nulo");

    return cyclistRepository
        .save(cyclistMapper.cyclistDTOToCyclist(updateCyclistDTO.getId()).apply(updateCyclistDTO))
        .map(Cyclist::getId);
  }
}
