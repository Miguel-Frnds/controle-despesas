package com.miguel.expense_control.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ExpenseItemCreateRequestDTO(
        @NotBlank(message = "O nome do item é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotNull(message = "O preço unitário é obrigatório")
        @Positive(message = "O preço unitário deve ser maior que zero")
        BigDecimal unitPrice,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity
) {
}
