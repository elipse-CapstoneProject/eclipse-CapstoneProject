package com.exception;
public class InvalidLoanTypeException extends RuntimeException {
    public InvalidLoanTypeException(int loanType) {
        super("Invalid loan type: " + loanType);
    }
    public InvalidLoanTypeException(String loanType) {
        super("Invalid loan type provided: " + loanType);
    }
}
