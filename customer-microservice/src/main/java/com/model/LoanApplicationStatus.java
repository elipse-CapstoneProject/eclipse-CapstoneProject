package com.model;

import com.google.protobuf.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loanapplicationstatus")
public class LoanApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_id")
    private Long applicationId;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="loan_id",nullable = false)
    private Integer loanId;

    @Column(name = "application_date", nullable = false)
    private java.sql.Timestamp applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public java.sql.Timestamp getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(java.sql.Timestamp timestamp) {
		this.applicationDate = timestamp;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LoanApplicationStatus(Long applicationId, Long customerId, Integer loanId, java.sql.Timestamp applicationDate,
			Status status) {
		super();
		this.applicationId = applicationId;
		this.customerId = customerId;
		this.loanId = loanId;
		this.applicationDate = applicationDate;
		this.status = status;
	}

	public LoanApplicationStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LoanApplicationStatus [applicationId=" + applicationId + ", customerId=" + customerId + ", loanId="
				+ loanId + ", applicationDate=" + applicationDate + ", status=" + status + "]";
	}

	
    
    
}