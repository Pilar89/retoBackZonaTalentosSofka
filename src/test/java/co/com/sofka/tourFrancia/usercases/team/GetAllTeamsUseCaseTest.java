package co.com.sofka.tourFrancia.usercases.team;
import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class GetAllTeamsUseCaseTest {
    @Mock
    TeamRepository teamRepository;


  GetAllTeamsUseCase getAllTeamsUseCase;


    @BeforeEach
    void setup() {
        TeamMapper teamMapper = new TeamMapper();
        getAllTeamsUseCase = new GetAllTeamsUseCase(teamRepository, teamMapper);
    }

    @Test
    void getAllTeamsTest(){
        var team = new Team();
        team.setId("12b");
        team.setTemaName("name");
        team.setCountry("country");

        when(teamRepository.findAll()).thenReturn(Flux.just(team));

        StepVerifier.create(getAllTeamsUseCase.get())
                .expectNextMatches(teamDTO -> {
                    assert teamDTO.getId().equals("12b");
                    assert teamDTO.getTemaName().equals("name");
                    assert teamDTO.getCountry().equals("country");
                    return true;
                })
                .verifyComplete();

        verify(teamRepository).findAll();

    }
}