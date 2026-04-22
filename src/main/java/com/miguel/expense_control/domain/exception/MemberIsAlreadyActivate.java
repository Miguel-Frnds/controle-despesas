package com.miguel.expense_control.domain.exception;

public class MemberIsAlreadyActivate extends RuntimeException {
    public MemberIsAlreadyActivate() {
        super("Membro ja esta ativo");
    }

    public MemberIsAlreadyActivate(String message) {
        super(message);
    }
}
