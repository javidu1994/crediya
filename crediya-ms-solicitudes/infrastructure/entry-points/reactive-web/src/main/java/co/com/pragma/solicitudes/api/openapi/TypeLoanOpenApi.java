package co.com.pragma.solicitudes.api.openapi;

import co.com.pragma.solicitudes.api.dto.TypeLoanDTO;
import co.com.pragma.solicitudes.api.dto.TypeLoanRequestDTO;
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
public class TypeLoanOpenApi {

    private final String SUCCESS = "Success";
    private final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    private final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());
    private final String INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    private final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public Builder saveTypeLoan(Builder builder) {
        return builder
                .operationId("saveTypeLoan")
                .description("Create a new type loan")
                .tag("TypeLoan")
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(TypeLoanRequestDTO.class))))
                .response(responseBuilder().responseCode(CREATED_CODE).description("Type Loan created")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(TypeLoanDTO.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE).description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getAllTypesLoan(Builder builder) {
        return builder
                .operationId("getAllTypesLoan")
                .description("Get all recorded types loan")
                .tag("TypeLoan")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(TypeLoanDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

}
