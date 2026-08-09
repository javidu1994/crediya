package co.com.pragma.solicitudes.r2dbc;

import co.com.pragma.solicitudes.r2dbc.entity.StateEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StateReactiveRepository
        extends ReactiveCrudRepository<StateEntity, Long>, ReactiveQueryByExampleExecutor<StateEntity> {

    Mono<StateEntity> findByName(String name);

}
