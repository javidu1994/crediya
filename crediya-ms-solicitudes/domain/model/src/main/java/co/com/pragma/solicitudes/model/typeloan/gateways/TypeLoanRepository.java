package co.com.pragma.solicitudes.model.typeloan.gateways;

import co.com.pragma.solicitudes.model.typeloan.TypeLoan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeLoanRepository {

    Mono<TypeLoan> save(TypeLoan typeLoan);

    Mono<TypeLoan> update(TypeLoan typeLoan);

    Mono<TypeLoan> findById(Long id);

    Mono<Void> deleteById(Long id);

    Mono<TypeLoan> findByName(String name);

    Flux<TypeLoan> findAll();

    Mono<Boolean> existTypeLoanByName(String name);
}
