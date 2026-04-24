package com.miguel.expense_control.domain.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(){
        super("Membro não encontrado");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
