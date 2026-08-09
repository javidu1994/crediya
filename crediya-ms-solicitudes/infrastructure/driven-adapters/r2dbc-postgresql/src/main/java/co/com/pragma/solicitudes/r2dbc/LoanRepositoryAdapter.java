package co.com.pragma.solicitudes.r2dbc;

import co.com.pragma.solicitudes.model.loan.Loan;
import co.com.pragma.solicitudes.model.loan.dto.LoanFilterRequest;
import co.com.pragma.solicitudes.model.loan.dto.LoanProjection;
import co.com.pragma.solicitudes.model.loan.gateways.LoanRepository;
import co.com.pragma.solicitudes.r2dbc.entity.LoanEntity;
import co.com.pragma.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class LoanRepositoryAdapter extends ReactiveAdapterOperations<Loan, LoanEntity, Long, LoanReactiveRepository>
        implements LoanRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanRepositoryAdapter.class);

    public LoanRepositoryAdapter(LoanReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Loan.class/* change for domain model */));
    }

    @Override
    public Mono<Loan> save(Loan loan) {
        return super.save(loan)
                .doOnSuccess(l -> LOGGER.debug("save loan: {}", l));
    }

    @Override
    public Mono<Loan> update(Loan loan) {
        return super.save(loan)
                .doOnSuccess(l -> LOGGER.debug("update loan: {}", l));
    }

    @Override
    public Flux<Loan> findAll() {
        return super.findAll()
                .doOnNext(l -> LOGGER.info("loans returned: {}", l));
    }

    @Override
    public Mono<Loan> findById(Long id) {
        return super.findById(id)
                .doOnNext(l -> LOGGER.debug("findById with param: {}", id));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id)
                .doOnSuccess(l -> LOGGER.debug("delete loan: {}", id));
    }

    @Override
    public Flux<Loan> findByEmail(String email) {
        return super.repository.findByEmail(email)
                .map(super::toEntity)
                .doOnNext(l -> LOGGER.debug("findByEmail with param: {} -> {}", email, l));
    }

    @Override
    public Flux<LoanProjection> findAllPaginated(LoanFilterRequest filter) {
        return super.repository.findAllPaged(filter.email(), filter.states(), filter.size(), filter.getOffset())
                .doOnNext(l -> LOGGER.debug("results paginated: {}", l));
    }

    @Override
    public Mono<Long> countAllPaged(String email, String[] states) {
        return super.repository.countAllPaged(email, states)
                .doOnNext(l -> LOGGER.debug("total results: {}", l));
    }
}
