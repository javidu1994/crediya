package co.com.pragma.solicitudes.usecase.typeloan;

import co.com.pragma.solicitudes.model.typeloan.TypeLoan;
import co.com.pragma.solicitudes.model.typeloan.gateways.TypeLoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TypeLoanUseCase {

    private final TypeLoanRepository typeLoanRepository;

    public Mono<TypeLoan> save(TypeLoan typeLoan) {
        return typeLoanRepository.existTypeLoanByName(typeLoan.getName())
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException("The name " + typeLoan.getName() + " is already registered")))
                .flatMap(exist -> typeLoanRepository.save(typeLoan));
    }

    public Mono<TypeLoan> findById(Long id) {
        return typeLoanRepository.findById(id);
    }

    public Flux<TypeLoan> findAll() {
        return typeLoanRepository.findAll();
    }

    public Mono<TypeLoan> update(TypeLoan typeLoan) {
        return typeLoanRepository.save(typeLoan);
    }

    public Mono<TypeLoan> findByName(String name) {
        return typeLoanRepository.findByName(name);
    }

    public Mono<Void> deleteById(Long userId) {
        return typeLoanRepository.deleteById(userId);
    }
}
