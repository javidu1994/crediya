package co.com.pragma.solicitudes.api.dto;

import co.com.pragma.solicitudes.model.state.State;
import co.com.pragma.solicitudes.model.typeloan.TypeLoan;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoanDTO {

    private Long idLoan;
    private BigDecimal amount;
    private Integer term;
    private String documentNumber;
    private String email;
    private Long idState;
    private Long idTypeLoan;
}
