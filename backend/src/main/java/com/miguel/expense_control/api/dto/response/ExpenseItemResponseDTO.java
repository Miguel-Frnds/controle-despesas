package com.miguel.expense_control.api.dto.response;

import java.math.BigDecimal;

public record ExpenseItemResponseDTO(
        String name,
        BigDecimal unitPrice,
        Integer quantity,
        Long expenseId
) {
}
