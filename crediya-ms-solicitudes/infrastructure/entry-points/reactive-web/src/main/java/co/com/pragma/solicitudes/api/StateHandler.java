package co.com.pragma.solicitudes.api;

import co.com.pragma.solicitudes.api.dto.StateDTO;
import co.com.pragma.solicitudes.api.dto.StateRequestDTO;
import co.com.pragma.solicitudes.api.dto.ValidationError;
import co.com.pragma.solicitudes.api.exception.ValidationException;
import co.com.pragma.solicitudes.model.state.State;
import co.com.pragma.solicitudes.usecase.state.StateUseCase;
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
public class StateHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(StateHandler.class);
    private final StateUseCase stateUseCase;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenPOSTSaveState(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(StateRequestDTO.class)
                .doOnNext(s -> LOGGER.debug("listenPOSTSaveState with data: {} ", s.toString()))
                .flatMap(this::validateRequest)
                .map(state -> objectMapper.map(state, State.class))
                .flatMap(stateUseCase::save)
                //.as(transactionalOperator::transactional)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idState}")
                                .build(saved.getIdState()))
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(saved)
                );
    }

    private Mono<StateRequestDTO> validateRequest(StateRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, StateRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            List<ValidationError> fieldErrors = errors.getFieldErrors()
                    .stream()
                    .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            LOGGER.info("Errors while listenPOSTSaveState: {}", fieldErrors);
            throw new ValidationException(fieldErrors);
        }
        return Mono.just(requestDTO);
    }

    public Mono<ServerResponse> listenGETStatesByName(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse("");

        if (name.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("The parameter 'name' is required");
        }
        return stateUseCase.findByName(name)
                .doOnNext(s -> LOGGER.debug("listenGETStatesByName with name: {} ", name))
                .flatMap(state -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(state))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listenGETAllStates(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(stateUseCase.findAll(), StateDTO.class);
    }
}
