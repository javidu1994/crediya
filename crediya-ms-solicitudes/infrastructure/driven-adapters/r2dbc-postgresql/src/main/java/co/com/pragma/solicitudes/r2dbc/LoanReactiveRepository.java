package co.com.pragma.solicitudes.r2dbc;

import co.com.pragma.solicitudes.model.loan.dto.LoanProjection;
import co.com.pragma.solicitudes.r2dbc.entity.LoanEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoanReactiveRepository extends ReactiveCrudRepository<LoanEntity, Long>,
        ReactiveQueryByExampleExecutor<LoanEntity>,
        ReactiveSortingRepository<LoanEntity, Long> {

    Flux<LoanEntity> findByEmail(String email);

    @Query("""
        SELECT l.id_loan, l.amount, l.term, l.email, t.interest_rate, s.name AS state_name, t.name AS type_loan_name,
        CASE WHEN s.name = 'Aprobada' THEN l.amount + (l.amount * (t.interest_rate / 100)) * l.term
            ELSE 0
            END AS total_monthly_debt
        FROM loans l
        JOIN states s ON l.id_state = s.id_state
        JOIN types_loan t ON l.id_type_loan = t.id_type_loan
        WHERE (:email IS NULL OR l.email = :email)
                      AND (:states IS NULL OR s.name = ANY (:states))
        LIMIT :size OFFSET :offset
    """)
    Flux<LoanProjection> findAllPaged(@Param("email") String email, @Param("states") String[] states,
                                      @Param("size") int size, @Param("offset") long offset);

    @Query("""
        SELECT count(*)
        FROM loans l
        JOIN states s ON l.id_state = s.id_state
        JOIN types_loan t ON l.id_type_loan = t.id_type_loan
        WHERE (:email IS NULL OR l.email = :email)
                      AND (:states IS NULL OR s.name = ANY (:states))
    """)
    Mono<Long> countAllPaged(@Param("email") String email, @Param("states") String[] states);

}
