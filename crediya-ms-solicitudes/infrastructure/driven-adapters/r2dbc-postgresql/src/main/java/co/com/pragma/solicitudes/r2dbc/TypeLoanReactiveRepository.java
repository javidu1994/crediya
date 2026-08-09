package co.com.pragma.solicitudes.r2dbc;

import co.com.pragma.solicitudes.r2dbc.entity.TypeLoanEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TypeLoanReactiveRepository
        extends ReactiveCrudRepository<TypeLoanEntity, Long>, ReactiveQueryByExampleExecutor<TypeLoanEntity> {

    Mono<TypeLoanEntity> findByName(String name);
}
