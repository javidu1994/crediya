package co.com.pragma.solicitudes.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class TypeLoanPath {

    private String typesLoan;
    private String typesLoanById;
    private String typesLoanByName;
}
