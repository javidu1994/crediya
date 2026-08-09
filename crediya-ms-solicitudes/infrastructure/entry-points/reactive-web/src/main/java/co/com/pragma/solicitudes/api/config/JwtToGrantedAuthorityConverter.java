package co.com.pragma.solicitudes.api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class JwtToGrantedAuthorityConverter implements Converter<Jwt, Flux<GrantedAuthority>> {

    @Override
    public Flux<GrantedAuthority> convert(Jwt jwt) {
        // Extraemos la lista de roles del claim "roles" del JWT
        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles == null) return Flux.empty();

        return Flux.fromIterable(roles)
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));
    }

}
