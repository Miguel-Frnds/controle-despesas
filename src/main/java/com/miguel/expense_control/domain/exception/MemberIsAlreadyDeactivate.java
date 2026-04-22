package com.miguel.expense_control.domain.exception;

public class MemberIsAlreadyDeactivate extends RuntimeException {
    public MemberIsAlreadyDeactivate(){
        super("Membro já esta inativo");
    }

    public MemberIsAlreadyDeactivate(String message) {
        super(message);
    }
}
