package co.com.pragma.solicitudes.r2dbc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("loans")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_loan")
    private Long idLoan;

    @Column("amount")
    private BigDecimal amount;

    @Column("term")
    private Integer term;

    @Column("document_number")
    private String documentNumber;

    @Column("email")
    private String email;

    @Column("id_state")
    private Long idState;

    @Column("id_type_loan")
    private Long idTypeLoan;

}
