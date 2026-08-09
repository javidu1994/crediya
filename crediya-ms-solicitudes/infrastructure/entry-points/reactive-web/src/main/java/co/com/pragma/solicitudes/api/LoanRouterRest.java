package co.com.pragma.solicitudes.api;

import co.com.pragma.solicitudes.api.config.LoanPath;
import co.com.pragma.solicitudes.api.openapi.LoanOpenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoanRouterRest {

    private final LoanPath loanPath;
    private final LoanHandler loanHandler;

    @Bean
    public RouterFunction<ServerResponse> loanRoutes(LoanHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(loanPath.getLoans(), loanHandler::listenPOSTSaveLoan, LoanOpenApi::saveLoan)
                .GET(loanPath.getLoans(), loanHandler::listenGETAllLoans, LoanOpenApi::getAllLoans)
                .GET(loanPath.getLoans() + loanPath.getLoansFull(), loanHandler::listenGETAllFullLoans,
                        LoanOpenApi::getAllFullLoans)
                .GET(loanPath.getLoans() + loanPath.getLoansPaginated(), loanHandler::listenGETLoansPaginated,
                        LoanOpenApi::getLoansPaginated)
                .build();
    }
}
