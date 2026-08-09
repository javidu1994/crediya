package co.com.pragma.solicitudes.model.user.gateways;

import co.com.pragma.solicitudes.model.user.dto.UserDTO;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Boolean> existUserByDni(String dni);

    Mono<UserDTO> findUserByEmail(String email);
}
