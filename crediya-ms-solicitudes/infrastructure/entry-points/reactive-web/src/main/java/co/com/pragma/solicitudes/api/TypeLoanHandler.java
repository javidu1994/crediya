package co.com.pragma.solicitudes.api;

import co.com.pragma.solicitudes.api.dto.*;
import co.com.pragma.solicitudes.api.exception.ValidationException;
import co.com.pragma.solicitudes.model.typeloan.TypeLoan;
import co.com.pragma.solicitudes.usecase.typeloan.TypeLoanUseCase;
import org.reactivecommons.utils.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TypeLoanHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(TypeLoanHandler.class);
    private final TypeLoanUseCase typeLoanUseCase;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenPOSTSaveTypeLoan(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TypeLoanRequestDTO.class)
                .doOnNext(s -> LOGGER.debug("listenPOSTSaveTypeLoan with data: {} ", s.toString()))
                .flatMap(this::validateRequest)
                .map(user -> objectMapper.map(user, TypeLoan.class))
                .flatMap(typeLoanUseCase::save)
                //.as(transactionalOperator::transactional)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idTypeLoan}")
                                .build(saved.getIdTypeLoan()))
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(saved)
                );
    }

    private Mono<TypeLoanRequestDTO> validateRequest(TypeLoanRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, TypeLoanRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            List<ValidationError> fieldErrors = errors.getFieldErrors()
                    .stream()
                    .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            LOGGER.info("Errors while listenPOSTSaveTypeLoan: {}", fieldErrors);
            throw new ValidationException(fieldErrors);
        }
        return Mono.just(requestDTO);
    }

    public Mono<ServerResponse> listenGETAllTypesLoan(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(typeLoanUseCase.findAll(), TypeLoanDTO.class);
    }
}
