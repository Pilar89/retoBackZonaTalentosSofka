package co.com.sofka.tourFrancia.routers;

import co.com.sofka.tourFrancia.model.CyclistDTO;
import co.com.sofka.tourFrancia.usercases.cyclist.CreateCyclistUseCase;
import co.com.sofka.tourFrancia.usercases.cyclist.DeleteCyclistUseCase;
import co.com.sofka.tourFrancia.usercases.cyclist.UpdateCyclisUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CyclistRouter {

  // crear registro
  @Bean
  public RouterFunction<ServerResponse> createCyclist(CreateCyclistUseCase useCase) {
    Function<CyclistDTO, Mono<ServerResponse>> executor =
        cyclistDTO ->
            useCase
                .apply(cyclistDTO)
                .flatMap(
                    result ->
                        ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(result));

    return route(
        POST("/createCyclist").and(accept(MediaType.APPLICATION_JSON)),
        request -> request.bodyToMono(CyclistDTO.class).flatMap(executor));
  }
  // actualizar registro

  @Bean
  public RouterFunction<ServerResponse> updateProject(UpdateCyclisUseCase updateCyclisUseCasee) {
    Function<CyclistDTO, Mono<ServerResponse>> executor =
        cyclistDTO ->
            updateCyclisUseCasee
                .apply(cyclistDTO)
                .flatMap(
                    result ->
                        ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(result));
    return route(
        PUT("/updateCyclist").and(accept(MediaType.APPLICATION_JSON)),
        request -> request.bodyToMono(CyclistDTO.class).flatMap(executor));
  }

  // eliminar registro

  @Bean
  public RouterFunction<ServerResponse> deleteCyclist(DeleteCyclistUseCase deleteCyclistUseCase) {
    return route(
        DELETE("/deleteCyclist/{id}").and(accept(MediaType.APPLICATION_JSON)),
        request ->
            ServerResponse.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromPublisher(
                        deleteCyclistUseCase.apply(request.pathVariable("id")), Void.class)));
  }
}
