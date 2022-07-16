package co.com.sofka.tourFrancia.usercases.cyclist;

import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.model.CyclistDTO;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    var prueba =1;
    List<String> list3 = new ArrayList<>();
    var v = 0;

    var cyclistList =
        cyclistRepository
            .findAllByTeamId(cyclistDTO.getTeamId()).collect(Collectors.toList()).share().block();
    System.out.println("size "+cyclistList.size());
    System.out.println("valor "+cyclistList);
    System.out.println("long "+v);
    if (cyclistList.size()<=8){
      return guardar(cyclistDTO);
    }
    System.out.println("No se puede agregar mas ciclistas, el equipo esta completo");
    return null;

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
