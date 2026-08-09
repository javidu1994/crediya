package co.com.pragma.solicitudes.usecase.loan.dto;

import java.util.List;

public record LoanPagedResponse<T>(
        List<T> content,
        long totalElements,
        int page,
        int size
) {}
