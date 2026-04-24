package com.miguel.expense_control.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ExpenseItemCreateRequestDTO(
        @NotBlank @Size(max = 100) String name,
        @NotNull @Positive BigDecimal unitPrice,
        @NotNull @Positive Integer quantity
) {
}
