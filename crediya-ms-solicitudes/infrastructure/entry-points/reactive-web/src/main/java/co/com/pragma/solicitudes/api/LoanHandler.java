package co.com.pragma.solicitudes.api;

import co.com.pragma.solicitudes.api.dto.*;
import co.com.pragma.solicitudes.api.exception.ValidationException;
import co.com.pragma.solicitudes.api.util.JwtService;
import co.com.pragma.solicitudes.model.loan.Loan;
import co.com.pragma.solicitudes.model.loan.dto.LoanFilterRequest;
import co.com.pragma.solicitudes.usecase.loan.LoanUseCase;
import co.com.pragma.solicitudes.usecase.loan.dto.LoanPagedResponse;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoanHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(LoanHandler.class);
    private final LoanUseCase loanUseCase;
    private final Validator validator;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;


    public Mono<ServerResponse> listenPOSTSaveLoan(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanRequestDTO.class)
                .flatMap(request -> jwtService.extractUsernameReactiveContext()
                        .filter(subject -> subject.equals(request.getEmail()))
                        .map(subject -> request) // Si pasa el filtro, devolvemos el request original
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                                HttpStatus.FORBIDDEN, "The loan user should be the same as logged!")))
                .doOnNext(l -> LOGGER.debug("listenPOSTSaveLoan with data: {} ", l.toString()))
                .flatMap(this::validateRequest)
                .map(state -> objectMapper.map(state, Loan.class))
                .flatMap(loanUseCase::save)
                //.as(transactionalOperator::transactional)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idState}")
                                .build(saved.getIdLoan()))
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(saved)
                );
    }

    /* ZipWith
    public Mono<ServerResponse> listenPOSTSaveLoan2(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanRequestDTO.class)
            .zipWith(jwtService.extractUsernameReactiveContext()) // Combina ambos Monos
            .filter(tuple -> {
                LoanRequestDTO request = tuple.getT1();
                String usernameFromContext = tuple.getT2();
                return request.getEmail().equals(usernameFromContext);
            })
            .map(tuple -> tuple.getT1()) // Volvemos a quedarnos solo con el DTO
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "The loan user should be the same as logged!")))
            .doOnNext(l -> LOGGER.debug("listenPOSTSaveLoan with data: {} ", l.toString()))
            .flatMap(this::validateRequest)
            .map(state -> objectMapper.map(state, Loan.class))
            .flatMap(loanUseCase::save)
            //.as(transactionalOperator::transactional)
            .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idState}")
                            .build(saved.getIdLoan()))
                    .contentType(MediaType.APPLICATION_NDJSON)
                    .bodyValue(saved)
            );
    }*/

    private Mono<LoanRequestDTO> validateRequest(LoanRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, LoanRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            List<ValidationError> fieldErrors = errors.getFieldErrors()
                    .stream()
                    .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            LOGGER.info("Errors while listenPOSTSaveLoan: {}", fieldErrors);
            throw new ValidationException(fieldErrors);
        }
        return Mono.just(requestDTO);
    }

    public Mono<ServerResponse> listenGETAllLoans(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(loanUseCase.findAll(), LoanDTO.class);
    }

    public Mono<ServerResponse> listenGETAllFullLoans(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(loanUseCase.getFullAllLoans(), LoanUseCase.LoanFullDTO.class);
    }

    public Mono<ServerResponse> listenGETLoansPaginated(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanFilterRequest.class)
                .flatMap( filter -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(loanUseCase.findAllPaginated(filter), LoanPagedResponse.class));
    }

}
