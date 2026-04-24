package com.miguel.expense_control.api.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ExpenseItemUpdateRequestDTO(
        @Size(max = 100) String name,
        @Positive BigDecimal unitPrice,
        @Positive Integer quantity
) {
}
