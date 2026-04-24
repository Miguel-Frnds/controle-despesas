package com.miguel.expense_control.domain.exception;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(String name){
        super(name + " já está em uso no sistema");
    }
}
