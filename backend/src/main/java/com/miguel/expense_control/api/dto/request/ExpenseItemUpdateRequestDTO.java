package com.miguel.expense_control.api.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ExpenseItemUpdateRequestDTO(
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @Positive(message = "O preço unitário deve ser maior que zero")
        BigDecimal unitPrice,

        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity
) {
}
