package com.miguel.expense_control.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberRequestDTO(@NotBlank @Size(min = 2, max = 150) String name) {
}
