package co.com.pragma.solicitudes.model.state.gateways;

import co.com.pragma.solicitudes.model.state.State;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StateRepository {

    Mono<State> save(State state);

    Mono<State> update(State state);

    Mono<State> findById(Long id);

    Mono<Void> deleteById(Long id);

    Mono<State> findByName(String name);

    Mono<Boolean> existStateByName(String name);

    Flux<State> findAll();
}
