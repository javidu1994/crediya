package co.com.pragma.solicitudes.r2dbc;

import co.com.pragma.solicitudes.model.state.State;
import co.com.pragma.solicitudes.model.state.gateways.StateRepository;
import co.com.pragma.solicitudes.r2dbc.entity.StateEntity;
import co.com.pragma.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class StateRepositoryAdapter extends ReactiveAdapterOperations<State, StateEntity, Long, StateReactiveRepository>
        implements StateRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateRepositoryAdapter.class);

    public StateRepositoryAdapter(StateReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, State.class));
    }

    @Override
    public Mono<State> save(State state) {
        return super.save(state)
                .doOnSuccess(s -> LOGGER.debug("save state: {}", s));
    }

    @Override
    public Mono<State> update(State state) {
        return super.save(state)
                .doOnSuccess(s -> LOGGER.debug("update loan: {}", s));
    }

    @Override
    public Flux<State> findAll() {
        return super.findAll()
                .doOnNext(s -> LOGGER.debug("states returned: {}", s));
    }

    @Override
    public Mono<State> findById(Long id) {
        return super.findById(id)
                .doOnNext(s -> LOGGER.debug("findById with param: {}", id));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return super.repository.deleteById(id)
                .doOnSuccess(s -> LOGGER.debug("deleteById with param: {}", id));
    }

    @Override
    public Mono<State> findByName(String name) {
        return super.repository.findByName(name)
                .map(super::toEntity)
                .doOnNext(s -> LOGGER.debug("findByName with param: {}", name));
    }

    @Override
    public Mono<Boolean> existStateByName(String name) {
        State state = State.builder()
                .name(name)
                .build();
        return super.findByExample(state)
                .doOnNext(exists -> LOGGER.debug("existStateByName with param: {} -> {}", name, exists))
                .next()
                .map(u -> true)
                .defaultIfEmpty(false);
    }
}
