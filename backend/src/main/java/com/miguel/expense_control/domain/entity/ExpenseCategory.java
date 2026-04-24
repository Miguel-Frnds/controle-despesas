package com.miguel.expense_control.domain.entity;

import lombok.Getter;

@Getter
public enum ExpenseCategory {
    FOOD("Alimentação");

    private final String description;

    private ExpenseCategory(String description){
        this.description = description;
    }
}
