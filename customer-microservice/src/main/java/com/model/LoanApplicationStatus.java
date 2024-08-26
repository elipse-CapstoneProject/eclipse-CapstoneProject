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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, unique = false)
    private Customer customer;

    @Column(name="loan_id",nullable = false)
    private Integer loanId;

    @Column(name = "application_date", nullable = false)
    private Timestamp applicationDate;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Timestamp getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Timestamp applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LoanApplicationStatus(Long applicationId, Customer customer, Integer loanId, Timestamp applicationDate,
			Status status) {
		super();
		this.applicationId = applicationId;
		this.customer = customer;
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
		return "LoanApplicationStatus [applicationId=" + applicationId + ", customer=" + customer + ", loanId=" + loanId
				+ ", applicationDate=" + applicationDate + ", status=" + status + "]";
	}

	public void setApplicationDate(java.sql.Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}
    
    
}