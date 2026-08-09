package co.com.pragma.solicitudes.model.loan.dto;

import java.util.List;

public record LoanFilterRequest(
        String email,
        String[] states,
        Integer page,
        Integer size
) {
    // Valores por defecto para evitar NullPointer
    public LoanFilterRequest {
        if (page == null) page = 0;
        if (size == null) size = 50;
        if (states != null && states.length == 0) states = null;
    }

    public long getOffset() {
        return (long) Math.max(this.page - 1, 0) * size;
    }
}
