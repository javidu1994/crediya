package co.com.pragma.solicitudes.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.security.Key;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    private Mono<String> getTokenReactiveContext() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth.getCredentials() instanceof Jwt)
                .map(auth -> (Jwt) auth.getCredentials())
                .map(jwt -> jwt)
                .map(Jwt::getTokenValue);
    }

    public Mono<String> extractUsername(String token) {
        return Mono.fromCallable(() -> Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Mono<String> extractUsernameReactiveContext() {
        return getTokenReactiveContext() // Retorna Mono<String>
                .flatMap(token -> extractUsername(token));
    }
}
