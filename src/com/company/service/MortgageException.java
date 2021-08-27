package com.company.service;

public class MortgageException extends RuntimeException {

    public MortgageException() {
        super("Case not handled");
    }
}
