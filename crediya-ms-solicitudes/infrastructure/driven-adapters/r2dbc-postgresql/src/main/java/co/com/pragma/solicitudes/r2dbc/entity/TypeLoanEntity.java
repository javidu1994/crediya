package co.com.pragma.solicitudes.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("types_loan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TypeLoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_type_loan")
    private Long idTypeLoan;

    @Column("name")
    private String name;

    @Column("minimum_amount")
    private BigDecimal minimumAmount;

    @Column("maximum_amount")
    private BigDecimal maximumAmount;

    @Column("interest_rate")
    private Double interestRate;

    @Column("automatic_validation")
    private Boolean automaticValidation;
}
