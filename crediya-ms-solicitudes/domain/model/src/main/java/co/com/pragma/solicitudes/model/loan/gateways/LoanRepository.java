package co.com.pragma.solicitudes.model.loan.gateways;

import co.com.pragma.solicitudes.model.loan.Loan;
import co.com.pragma.solicitudes.model.loan.dto.LoanFilterRequest;
import co.com.pragma.solicitudes.model.loan.dto.LoanProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoanRepository {

    Mono<Loan> save(Loan loan);

    Flux<Loan> findAll();

    Mono<Loan> update(Loan loan);

    Mono<Loan> findById(Long id);

    Mono<Void> deleteById(Long loanId);

    Flux<Loan> findByEmail(String email);

    Flux<LoanProjection> findAllPaginated(LoanFilterRequest filter);

    Mono<Long> countAllPaged(String email, String[] states);
}
