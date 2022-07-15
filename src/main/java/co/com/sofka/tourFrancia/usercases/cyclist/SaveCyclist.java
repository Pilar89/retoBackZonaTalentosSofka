package co.com.sofka.tourFrancia.usercases.cyclist;

import co.com.sofka.tourFrancia.model.CyclistDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveCyclist {
  Mono<String> apply(@Valid CyclistDTO cyclistDTO);
}
