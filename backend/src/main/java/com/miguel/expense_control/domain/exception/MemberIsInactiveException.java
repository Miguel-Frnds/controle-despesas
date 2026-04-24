package com.miguel.expense_control.domain.exception;

public class MemberIsInactiveException extends RuntimeException {
    public MemberIsInactiveException(String name) {
      super("Já existe um membro com o nome '" + name + "' inativo, reative-o ao invés de criar um novo.");
    }
}
