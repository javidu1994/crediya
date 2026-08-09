package co.com.pragma.solicitudes.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}") // Define tu clave en application.yml
    private String jwtSecret;
    private final JwtToGrantedAuthorityConverter jwtGrantedAuthoritiesConverter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/loans").hasAnyRole("CLIENTE")
                        .pathMatchers(HttpMethod.GET, "/api/v1/loans").hasAnyRole("ASESOR")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {
                            jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor());
                            jwt.jwtDecoder(jwtDecoder());
                        })
                )
                //.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    // Método para convertir los claims del JWT (ej: "roles": ["ADMIN"])
    // en SimpleGrantedAuthority ("ROLE_ADMIN")
    private ReactiveJwtAuthenticationConverter grantedAuthoritiesExtractor() {
        ReactiveJwtAuthenticationConverter jwtConverter = new ReactiveJwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // Es vital que el algoritmo coincida exactamente con el del error
        SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(), MacAlgorithm.HS512.name());

        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
