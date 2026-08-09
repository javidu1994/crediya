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
public class LoanRequestDTO {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Term is required")
    private Integer term;

    @NotBlank(message = "DNI is required")
    private String documentNumber;

    @NotBlank(message = "Email is required")
    private String email;

    private Long idState;

    @NotNull(message = "Type Loan is required")
    private Long idTypeLoan;

}
