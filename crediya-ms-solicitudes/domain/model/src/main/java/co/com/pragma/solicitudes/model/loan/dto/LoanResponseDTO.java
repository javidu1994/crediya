package co.com.pragma.solicitudes.model.loan.dto;

import java.math.BigDecimal;

public record LoanResponseDTO(
        Long id,
        BigDecimal amount,
        Integer term,
        String email,
        String userName, // Viene de Microservicio Auth
        Double interestRate,
        String stateName,
        String typeLoanName,
        BigDecimal baseSalary, // Viene de Microservicio Auth
        BigDecimal totalMonthlyDebt
) {}
