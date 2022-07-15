package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.model.TeamDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveTeam {
  Mono<String> apply(@Valid TeamDTO teamDTO);
}
