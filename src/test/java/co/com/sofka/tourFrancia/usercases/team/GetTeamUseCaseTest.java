package co.com.sofka.tourFrancia.usercases.team;


import co.com.sofka.tourFrancia.collections.Cyclist;
import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetTeamUseCaseTest {

   GetTeamUseCase getTeamUseCase;

    @Mock
    TeamRepository teamRepository;

    @Mock
    CyclistRepository cyclistRepository;

    TeamMapper teamMapper = new TeamMapper();
    CyclistMapper cyclistMapper = new CyclistMapper();

    @BeforeEach
    void setup() {
       getTeamUseCase = new GetTeamUseCase(teamRepository,cyclistRepository, teamMapper, cyclistMapper);
    }


    @Test
    void setGetProjectUseCaseTest() {
        var cyclist = new Cyclist();
        cyclist.setId("21b");
        cyclist.setTeamId("33a");
        cyclist.setName("name");
        cyclist.setNationality("nationality");

        var cyclistDTO = cyclistMapper.cyclistToCyclistDTO().apply(cyclist);
        var team = new Team();
        team.setId("33a");
        team.setTemaName("teamName");
        team.setCountry("country");
        when(teamRepository.findById(team.getId())).thenReturn(Mono.just(team));
        when(cyclistRepository.findAllByTeamId(team.getId())).thenReturn(Flux.just(cyclist));


        StepVerifier.create(getTeamUseCase.apply(team.getId()))
                .expectNextMatches(teamDTO -> {

                    assert  teamDTO.getId().equals(team.getId());
                    assert  teamDTO.getTemaName().equals(team.getTemaName());
                    assert teamDTO.getCountry().equals(team.getCountry());
                    return true;
                })
                .verifyComplete();

        verify(teamRepository).findById(team.getId());
        verify(cyclistRepository).findAllByTeamId(team.getId());

    }

}