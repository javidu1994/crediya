package co.com.pragma.solicitudes.model.loan;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {

    private Long idLoan;
    private BigDecimal amount;
    private Integer term;
    private String documentNumber;
    private String email;
    private Long idState;
    private Long idTypeLoan;
}
