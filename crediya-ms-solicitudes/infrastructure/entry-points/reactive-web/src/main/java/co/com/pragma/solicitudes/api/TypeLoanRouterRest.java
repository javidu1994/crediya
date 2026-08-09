package co.com.pragma.solicitudes.api;

import co.com.pragma.solicitudes.api.config.TypeLoanPath;
import co.com.pragma.solicitudes.api.openapi.TypeLoanOpenApi;
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
public class TypeLoanRouterRest {

    private final TypeLoanPath tyeLoanPath;
    private final TypeLoanHandler typeLoanHandler;

    @Bean
    public RouterFunction<ServerResponse> typeLoanRoutes(StateHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(tyeLoanPath.getTypesLoan(), typeLoanHandler::listenPOSTSaveTypeLoan, TypeLoanOpenApi::saveTypeLoan)
                .GET(tyeLoanPath.getTypesLoan(), typeLoanHandler::listenGETAllTypesLoan, TypeLoanOpenApi::getAllTypesLoan)
                .build();
    }


    /*@RouterOperations({ @RouterOperation(path = "/api/usecase/path", beanClass = StateHandler.class, beanMethod = "listenGETUseCase"),
                @RouterOperation(path = "/api/usecase/otherpath", beanClass = StateHandler.class, beanMethod = "listenPOSTUseCase"),
                @RouterOperation(path = "/api/otherusercase/path", beanClass = StateHandler.class, beanMethod = "listenGETOtherUseCase") })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(StateHandler stateHandler) {
        return route(GET("/api/usecase/path"), stateHandler::listenGETUseCase)
                .andRoute(POST("/api/usecase/otherpath"), stateHandler::listenPOSTUseCase)
                .and(route(GET("/api/otherusercase/path"), stateHandler::listenGETOtherUseCase));
    }*/
}
