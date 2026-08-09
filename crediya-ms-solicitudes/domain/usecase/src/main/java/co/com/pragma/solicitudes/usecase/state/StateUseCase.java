package co.com.pragma.solicitudes.usecase.state;

import co.com.pragma.solicitudes.model.state.State;
import co.com.pragma.solicitudes.model.state.gateways.StateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StateUseCase {

    private final StateRepository stateRepository;

    public Mono<State> save(State state) {
        return stateRepository.existStateByName(state.getName())
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException("The name " + state.getName() + " is already registered")))
                .flatMap(exist -> stateRepository.save(state));
    }

    public Mono<State> findById(Long id) {
        return stateRepository.findById(id);
    }

    public Flux<State> findAll() {
        return stateRepository.findAll();
    }

    public Mono<State> update(State state) {
        return stateRepository.save(state);
    }

    public Mono<State> findByName(String name) {
        return stateRepository.findByName(name);
    }

    public Mono<Void> deleteById(Long userId) {
        return stateRepository.deleteById(userId);
    }
}
