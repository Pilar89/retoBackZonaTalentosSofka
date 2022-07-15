package co.com.sofka.tourFrancia.usercases.cyclist;

import co.com.sofka.tourFrancia.collections.Cyclist;
import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.usercases.team.GetTeamUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateCyclisUseCaseTest {

    @Mock
    CyclistRepository cyclistRepository;
    UpdateCyclisUseCase updateCyclisUseCase;
    GetTeamUseCase getTeamUseCase;


   CyclistMapper cyclistMapper;

    @BeforeEach
    public void setup(){
        cyclistMapper = new CyclistMapper();
        updateCyclisUseCase = new UpdateCyclisUseCase(cyclistRepository, cyclistMapper,getTeamUseCase);
    }

    @Test
    void updateCyclisttUseCaseTest(){
        var cyclist = new Cyclist();
        cyclist.setId("af12");
        cyclist.setTeamId("jh3123");
        cyclist.setName("name");
        cyclist.setNationality("nationality");

        var cyclistReturn = new Cyclist();
        cyclistReturn.setId("af12");
        cyclistReturn.setTeamId("jh3123");
        cyclistReturn.setName("name1");
        cyclistReturn.setNationality("nationality1");

        var cyclistDTO =cyclistMapper.cyclistToCyclistDTO().apply(cyclist);

        when(cyclistRepository.findById("af12")).thenReturn(Mono.just(cyclist));
        when(cyclistRepository.save(cyclist)).thenReturn(Mono.just(cyclistReturn));

        StepVerifier.create(updateCyclisUseCase.apply(cyclistDTO))
                .expectNext("af12")
                .verifyComplete();

        verify(cyclistRepository).save(cyclist);


    }



}