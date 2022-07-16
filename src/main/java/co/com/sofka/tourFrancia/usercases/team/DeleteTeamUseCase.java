package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.repositories.TeamRepository;
import com.mongodb.Function;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class DeleteTeamUseCase implements Function<String, Mono<Void>> {
  private final TeamRepository teamRepository;

  public DeleteTeamUseCase(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  @Override
  public Mono<Void> apply(String id) {
    Objects.requireNonNull(id, "Id es requerido");

    return teamRepository
        .findById(id)
        .flatMap(
            project -> {
              return teamRepository.deleteById(id);
            });
  }
}
