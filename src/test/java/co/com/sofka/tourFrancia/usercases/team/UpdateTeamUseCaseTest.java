package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.collections.Team;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class UpdateTeamUseCaseTest {

    @Mock
    TeamRepository teamRepository;


    UpdateTeamUseCase  updateTeamUseCase;

    TeamMapper teamMapper;

    @BeforeEach
    public void setup(){
        teamMapper = new TeamMapper();
        updateTeamUseCase = new UpdateTeamUseCase(teamRepository, teamMapper);
    }

    @Test
    void updateProjectUseCaseTest(){
        var team = new Team();
        team.setId("13ab");
        team.setTemaName("teamName");
        team.setCountry("country");


        var teamReturn = new Team();
        teamReturn.setId("13ab");
        teamReturn.setTemaName("teamName1");
        teamReturn.setCountry("country1");


        var teamDTO = teamMapper.entityToTeamDTO().apply(team);
        when(teamRepository.findById("13ab")).thenReturn(Mono.just(team));
        when(teamRepository.save(team)).thenReturn(Mono.just(teamReturn));

        StepVerifier.create(updateTeamUseCase.apply(teamDTO))
                .expectNext("13ab")
                .verifyComplete();

        verify(teamRepository).save(team);


    }


}