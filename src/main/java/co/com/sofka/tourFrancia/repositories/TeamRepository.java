package co.com.sofka.tourFrancia.repositories;


import co.com.sofka.tourFrancia.collections.Team;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TeamRepository extends ReactiveCrudRepository<Team, String> {
  Flux<Team> findByCountry(String country);
}
