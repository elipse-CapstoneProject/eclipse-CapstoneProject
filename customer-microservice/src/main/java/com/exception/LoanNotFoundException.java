package com.exception;

public class LoanNotFoundException extends RuntimeException {
	public LoanNotFoundException(Long applicationId) {
        super("Loan with ID " + applicationId + " not found");
    }
	public LoanNotFoundException(int applicationId) {
        super("Loan with ID " + applicationId + " not found");
    }
    public LoanNotFoundException(String message) {
        super(message);
    }
}