package co.com.pragma.solicitudes.api.openapi;

import co.com.pragma.solicitudes.api.dto.LoanDTO;
import co.com.pragma.solicitudes.api.dto.LoanRequestDTO;
import co.com.pragma.solicitudes.usecase.loan.LoanUseCase;
import co.com.pragma.solicitudes.usecase.loan.dto.LoanPagedResponse;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class LoanOpenApi {

    private final String SUCCESS = "Success";
    private final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    private final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());
    private final String INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    private final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public Builder saveLoan(Builder builder) {
        return builder
                .operationId("saveLoan")
                .description("Create a new loan")
                .tag("Loan")
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(LoanRequestDTO.class))))
                .response(responseBuilder().responseCode(CREATED_CODE).description("State created")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(LoanDTO.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE).description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getAllLoans(Builder builder) {
        return builder
                .operationId("getAllLoans")
                .description("Get all recorded loans")
                .tag("Loan")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(LoanDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getAllFullLoans(Builder builder) {
        return builder
                .operationId("getAllFullLoans")
                .description("Get all recorded full loans")
                .tag("Loan")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(LoanUseCase.LoanFullDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getLoansPaginated(Builder builder) {
        return builder
                .operationId("getLoansPaginated")
                .description("Get loans paginated")
                .tag("Loan")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(LoanPagedResponse.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

}
