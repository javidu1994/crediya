package co.com.pragma.solicitudes.r2dbc;

import co.com.pragma.solicitudes.model.typeloan.TypeLoan;
import co.com.pragma.solicitudes.model.typeloan.gateways.TypeLoanRepository;
import co.com.pragma.solicitudes.r2dbc.entity.TypeLoanEntity;
import co.com.pragma.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TypeLoanRepositoryAdapter extends ReactiveAdapterOperations<TypeLoan, TypeLoanEntity, Long, TypeLoanReactiveRepository>
        implements TypeLoanRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeLoanRepositoryAdapter.class);

    public TypeLoanRepositoryAdapter(TypeLoanReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, TypeLoan.class));
    }

    @Override
    public Mono<TypeLoan> save(TypeLoan loan) {
        return super.save(loan)
                .doOnSuccess(l -> LOGGER.debug("save type loan: {}", l));
    }

    @Override
    public Mono<TypeLoan> update(TypeLoan loan) {
        return super.save(loan)
                .doOnSuccess(l -> LOGGER.debug("update type loan: {}", l));
    }

    @Override
    public Flux<TypeLoan> findAll() {
        return super.findAll()
                .doOnNext(l -> LOGGER.debug("types loan returned: {}", l));
    }

    @Override
    public Mono<TypeLoan> findById(Long id) {
        return super.findById(id)
                .doOnNext(l -> LOGGER.debug("findById with param: {}", id));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id)
                .doOnSuccess(u -> LOGGER.debug("delete type loan: {}", id));
    }

    @Override
    public Mono<TypeLoan> findByName(String name) {
        TypeLoan typeLoan = TypeLoan.builder()
                .name(name)
                .build();
        return super.findByExample(typeLoan)
                .doOnNext(s -> LOGGER.debug("findByName with param: {}", name))
                .next();
    }

    @Override
    public Mono<Boolean> existTypeLoanByName(String name) {
        TypeLoan typeLoan = TypeLoan.builder()
                .name(name)
                .build();
        return super.findByExample(typeLoan)
                .doOnNext(exists -> LOGGER.debug("existTypeLoanByName with param: {} -> {}", name, exists))
                .next()
                .map(u -> true)
                .defaultIfEmpty(false);
    }

}
