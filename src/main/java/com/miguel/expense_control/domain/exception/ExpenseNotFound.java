package com.miguel.expense_control.domain.exception;

public class ExpenseNotFound extends RuntimeException {
  public ExpenseNotFound() {
      super("Despesa não encontrada");
  }

  public ExpenseNotFound(String message) {
        super(message);
    }
}
