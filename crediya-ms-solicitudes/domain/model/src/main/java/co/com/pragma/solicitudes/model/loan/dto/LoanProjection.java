package co.com.pragma.solicitudes.model.loan.dto;

import java.math.BigDecimal;

public record LoanProjection(
        Long idLoan,
        BigDecimal amount,
        Integer term,
        String email,
        Double interestRate,
        String stateName,
        String typeLoanName,
        BigDecimal totalMonthlyDebt
) {}
