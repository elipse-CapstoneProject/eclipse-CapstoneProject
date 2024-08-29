package com.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
	
@Entity
@Table(name = "Loanms")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Integer loanId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_loan", nullable = false)
    @NotNull(message = "Type of loan cannot be null")
    private LoanType typeOfLoan;

    @Column(name = "loan_name", nullable = false)
    @NotBlank(message = "Loan name cannot be blank")
    private String loanName;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "term_months", nullable = false)
    @Min(value = 5, message = "Term months must be at least 5")
    @Max(value = 360, message = "Term months cannot exceed 360")
    private Integer termMonths;

    @Column(name = "max_amount", nullable = false)
    private BigDecimal maxAmount;

    @Column(name = "min_amount", nullable = false)
    private BigDecimal minAmount;


    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public LoanType getTypeOfLoan() {
        return typeOfLoan;
    }

    public void setTypeOfLoan(LoanType typeOfLoan) {
        this.typeOfLoan = typeOfLoan;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public enum LoanType {
        HOME,
        PERSONAL,
        STUDENT,
        BUSINESS
    }
}
