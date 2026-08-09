package co.com.pragma.solicitudes.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TypeLoanRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Minimum Amount is required")
    private BigDecimal minimumAmount;

    @NotNull(message = "Maximum Amount is required")
    private BigDecimal maximumAmount;

    @NotNull(message = "Interest Rate is required")
    private Double interestRate;

    @NotNull(message = "Automatic Validation is required")
    private Boolean automaticValidation;

}
