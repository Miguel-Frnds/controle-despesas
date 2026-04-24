package com.miguel.expense_control.domain.exception;

public class ExpenseItemNotFoundException extends RuntimeException {
    public ExpenseItemNotFoundException(){
        super("Item não foi encontrado");
    }
    public ExpenseItemNotFoundException(String message) {
        super(message);
    }
}
