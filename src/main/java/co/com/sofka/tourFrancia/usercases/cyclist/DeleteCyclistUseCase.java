package co.com.sofka.tourFrancia.usercases.cyclist;

import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import com.mongodb.Function;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class DeleteCyclistUseCase implements Function<String, Mono<Void>> {

  private final CyclistRepository cyclistRepository;

  public DeleteCyclistUseCase(CyclistRepository cyclistRepository) {
    this.cyclistRepository = cyclistRepository;
  }

  @Override
  public Mono<Void> apply(String id) {
    Objects.requireNonNull(id, "Id es requerido");
    return cyclistRepository
        .findById(id)
        .flatMap(
            project -> {
              return cyclistRepository.deleteById(id);
            });
  }
}
