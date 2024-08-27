package com.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class LoanApplicationRequest {

	private Integer loanId;
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
