/*package co.com.pragma.solicitudes.api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extraerAutoridades(jwt);
        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }

    private Collection<GrantedAuthority> extraerAutoridades(Jwt jwt) {
        // Extraemos la lista de "roles" del token
        Object roles = jwt.getClaims().get("roles");

        if (roles instanceof Collection<?>) {
            return ((Collection<?>) roles).stream()
                    .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.toString()))
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}*/
