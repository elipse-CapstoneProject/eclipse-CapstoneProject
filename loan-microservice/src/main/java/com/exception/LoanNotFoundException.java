package com.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(int loanId) {
        super();
    }

    public LoanNotFoundException(String message) {
        super(message);
    }
}