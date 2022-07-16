package co.com.sofka.tourFrancia.routers;

import co.com.sofka.tourFrancia.model.TeamDTO;
import co.com.sofka.tourFrancia.usercases.team.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TeamRouter {
  // Registrar un equipo
  @Bean
  public RouterFunction<ServerResponse> createTeam(CreateTeamUseCase createTeamUseCase) {
    Function<TeamDTO, Mono<ServerResponse>> executor =
        teamtDTO ->
            createTeamUseCase
                .apply(teamtDTO)
                .flatMap(
                    result ->
                        ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(result));
    return route(
        POST("/createTeam").and(accept(MediaType.APPLICATION_JSON)),
        request -> request.bodyToMono(TeamDTO.class).flatMap(executor));
  }

  // Consultar Todos los equipos

  @Bean
  public RouterFunction<ServerResponse> getAllTeams(GetAllTeamsUseCase getAllTeamsUseCase) {
    return route(
        GET("/getAllTeams"),
        request ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(getAllTeamsUseCase.get(), TeamDTO.class)));
  }

  // Fitrar equipo por country

  @Bean
  public RouterFunction<ServerResponse> getTeamByCountry(
      GetTeamByCountryUseCase getTeamByCountryUseCase) {
    return route(
        GET("/getTeamByCountry/{country}").and(accept(MediaType.APPLICATION_JSON)),
        request ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromPublisher(
                        getTeamByCountryUseCase.apply(request.pathVariable("country")),
                        TeamDTO.class)));
  }

  // Consultar un equipos por su id

  @Bean
  public RouterFunction<ServerResponse> getTeamById(GetTeamUseCase getTeamUseCase) {
    return route(
        GET("/getTeamById/{id}").and(accept(MediaType.APPLICATION_JSON)),
        request ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromPublisher(
                        getTeamUseCase.apply(request.pathVariable("id")), TeamDTO.class)));
  }

  // filtrar por id y nacionalidad de equipo

  @Bean
  public RouterFunction<ServerResponse> getTeamByIdAndNationality(GetTeamUseCase getTeamUseCase) {
    return route(
        GET("/getByIdAndNationality/{id}/{nationality}").and(accept(MediaType.APPLICATION_JSON)),
        request ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromPublisher(
                        getTeamUseCase
                            .apply(request.pathVariable("id"))
                            .map(
                                team -> {
                                  var cyclistFilter =
                                      team.getCyclists().stream()
                                          .filter(
                                              cyclist ->
                                                  cyclist
                                                      .getNationality()
                                                      .equalsIgnoreCase(
                                                          request.pathVariable("nationality")))
                                          .collect(Collectors.toList());
                                  team.setCyclists(cyclistFilter);
                                  return team;
                                }),
                        TeamDTO.class)));
  }

  //    Editar un equipo
  @Bean
  public RouterFunction<ServerResponse> updateTeam(UpdateTeamUseCase updateTeamUseCase) {
    Function<TeamDTO, Mono<ServerResponse>> executor =
        teamDTO ->
            updateTeamUseCase
                .apply(teamDTO)
                .flatMap(
                    result ->
                        ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(result));
    return route(
        PUT("/updateTeam").and(accept(MediaType.APPLICATION_JSON)),
        request -> request.bodyToMono(TeamDTO.class).flatMap(executor));
  }

  // eliminar equipo
  @Bean
  public RouterFunction<ServerResponse> deleteTeam(DeleteTeamUseCase deleteTeamUseCase) {
    return route(
        DELETE("/deleteTeam/{id}").and(accept(MediaType.APPLICATION_JSON)),
        request ->
            ServerResponse.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromPublisher(
                        deleteTeamUseCase.apply(request.pathVariable("id")), Void.class)));
  }
}
