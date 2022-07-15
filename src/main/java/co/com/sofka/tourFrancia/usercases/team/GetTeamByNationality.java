package co.com.sofka.tourFrancia.usercases.team;

import co.com.sofka.tourFrancia.mappers.CyclistMapper;
import co.com.sofka.tourFrancia.mappers.TeamMapper;
import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.repositories.CyclistRepository;
import co.com.sofka.tourFrancia.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetTeamByNationality {}
