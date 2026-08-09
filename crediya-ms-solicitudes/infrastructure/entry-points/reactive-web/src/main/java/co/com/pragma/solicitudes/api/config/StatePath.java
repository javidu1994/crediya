package co.com.pragma.solicitudes.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class StatePath {

    private String states;
    private String statesById;
    private String statesByName;
}
