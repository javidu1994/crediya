package co.com.pragma.solicitudes.usecase.loan;

import co.com.pragma.solicitudes.model.loan.Loan;
import co.com.pragma.solicitudes.model.loan.dto.LoanFilterRequest;
import co.com.pragma.solicitudes.model.loan.dto.LoanResponseDTO;
import co.com.pragma.solicitudes.model.loan.gateways.LoanRepository;
import co.com.pragma.solicitudes.model.state.State;
import co.com.pragma.solicitudes.model.state.gateways.StateRepository;
import co.com.pragma.solicitudes.model.typeloan.TypeLoan;
import co.com.pragma.solicitudes.model.typeloan.gateways.TypeLoanRepository;
import co.com.pragma.solicitudes.model.user.gateways.UserRepository;
import co.com.pragma.solicitudes.usecase.loan.dto.LoanPagedResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class LoanUseCase {

    private static final Logger LOGGER = Logger.getLogger(LoanUseCase.class.getName());
    private final LoanRepository loanRepository;
    private final TypeLoanRepository typeLoanRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;
    private final String INITIAL_STATE = "Pendiente de revisión";

    @Builder(toBuilder = true)
    public record LoanFullDTO(Long idLoan,
                              BigDecimal amount,
                              Integer term,
                              String documentNumber,
                              String email,
                              State state,
                              TypeLoan typeLoan) {}

    //primera opcion
    public Mono<Loan> save(Loan loan) {
        return userRepository.existUserByDni(loan.getDocumentNumber())
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("The user with document " + loan.getDocumentNumber() + " does not exist!")))
                .flatMap(exist -> typeLoanRepository.findById(loan.getIdTypeLoan()))
                .switchIfEmpty(Mono.error(
                        new RuntimeException("The type loan with ID " + loan.getIdTypeLoan() + " not exists!")))
                .flatMap(typeLoan -> stateRepository.findByName(INITIAL_STATE))
                .switchIfEmpty(Mono.error(new RuntimeException("Default State " + INITIAL_STATE + " not found!")))
                .flatMap(state -> {
                    loan.setIdState(state.getIdState());
                    return loanRepository.save(loan);
                })
                .doOnSuccess(s -> LOGGER.info("Loan saved successful with ID " + s.getIdLoan()))
                .onErrorResume(e -> {
                    return Mono.error(new RuntimeException("Error on save: " + e.getMessage()));
                });
    }

    /* Segunda opcion
    public Mono<Loan> save2(Loan loan) {
        return userRepository.existUserByDni(loan.getDocumentNumber())
                .flatMap(exist -> {
                    if (!exist) {
                        return Mono.error(
                            new RuntimeException("The user with document " + loan.getDocumentNumber() + " does not exist in Authentication service!"));
                    }
                // 2. Si existe, procedemos con la validación del tipo de préstamo
                    return typeLoanRepository.findById(loan.getIdTypeLoan());
                })
                .switchIfEmpty(Mono.error(
                        new RuntimeException("The type loan with ID " + loan.getIdTypeLoan() + " not exists!")))
                .flatMap(typeLoan -> stateRepository.findByName(INITIAL_STATE))
                .switchIfEmpty(Mono.error(new RuntimeException("Default State " + INITIAL_STATE + " not found!")))
                    .flatMap(state -> {
                        loan.setIdState(state.getIdState());
                        return loanRepository.save(loan);
                    });
    }*/

    public Mono<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    public Flux<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Mono<Loan> update(Loan loan) {
        return loanRepository.save(loan);
    }

    public Mono<Void> deleteById(Long userId) {
        return loanRepository.deleteById(userId);
    }

    public Flux<LoanFullDTO> getFullAllLoans() {
        return loanRepository.findAll()
                .flatMap(loanEntity -> {
                    Mono<State> stateMono = stateRepository.findById(loanEntity.getIdState());
                    Mono<TypeLoan> typeMono = typeLoanRepository.findById(loanEntity.getIdTypeLoan());

                    return Mono.zip(stateMono, typeMono)
                            .map(tuple -> LoanFullDTO.builder()
                                    .idLoan(loanEntity.getIdLoan())
                                    .amount(loanEntity.getAmount())
                                    .term(loanEntity.getTerm())
                                    .documentNumber(loanEntity.getDocumentNumber())
                                    .email(loanEntity.getEmail())
                                    .state(tuple.getT1())
                                    .typeLoan(tuple.getT2())
                                    .build()
                            );
                });
    }

    public Mono<LoanPagedResponse> findAllPaginated(LoanFilterRequest filter) {
        // 1. Obtener los datos paginados y enriquecidos con el microservicio Auth
        Mono<List<LoanResponseDTO>> data = loanRepository.findAllPaginated(filter)
                .flatMap(loan -> userRepository.findUserByEmail(loan.email())
                        .map(user -> new LoanResponseDTO(
                                loan.idLoan(),
                                loan.amount(),
                                loan.term(),
                                loan.email(),
                                user.getName(),
                                loan.interestRate(),
                                loan.stateName(),
                                loan.typeLoanName(),
                                user.getBaseSalary(),
                                loan.totalMonthlyDebt()
                        )))
                .collectList();

        // 2. Obtener el conteo total (necesario para el frontend)
        Mono<Long> total = loanRepository.countAllPaged(filter.email(), filter.states());

        // 3. Combinar ambos
        return Mono.zip(data, total)
                .map(tuple -> new LoanPagedResponse<>(
                        tuple.getT1(),
                        tuple.getT2(),
                        filter.page(),
                        filter.size()
                ));
    }
}
