package com.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class LoanApplicationRequest {

	private Integer loanId;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public LoanApplicationRequest(Integer loanId) {
		super();
		this.loanId = loanId;
	}

	public LoanApplicationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

   
}
