package com.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LoanApplicationRequest {

	@NotNull(message = "Loan ID cannot be null")
    @Positive(message = "Loan ID must be a positive number")
    private Integer loanId;

    @NotNull(message = "Customer ID cannot be null")
    @Positive(message = "Customer ID must be a positive number")
    private Long customerId;

	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public LoanApplicationRequest(Integer loanId, Long customerId) {
		super();
		this.loanId = loanId;
		this.customerId = customerId;
	}
	public LoanApplicationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	

   
}
