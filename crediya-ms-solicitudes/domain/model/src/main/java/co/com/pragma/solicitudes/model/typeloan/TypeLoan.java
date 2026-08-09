package co.com.pragma.solicitudes.model.typeloan;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TypeLoan {

    private Long idTypeLoan;
    private String name;
    private BigDecimal minimumAmount;
    private BigDecimal maximumAmount;
    private Double interestRate;
    private Boolean automaticValidation;
}
