package co.com.pragma.solicitudes.api.exception;

import co.com.pragma.solicitudes.api.dto.ValidationError;

import java.util.Arrays;
import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<co.com.pragma.solicitudes.api.dto.ValidationError> errors;

    public ValidationException(ValidationError... errors) {
        this(Arrays.asList(errors));
    }

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
