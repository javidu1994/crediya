package co.com.pragma.solicitudes.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeLoanDTO {

    private Long idTypeLoan;
    private String name;
    private BigDecimal minimumAmount;
    private BigDecimal maximumAmount;
    private Double interestRate;
    private Boolean automaticValidation;
}
