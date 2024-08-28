package com.model;

import java.math.BigDecimal;

import com.model.Loan.LoanType;

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
import jakarta.validation.constraints.NotNull;

	@Entity
	@Table(name = "Loan")
	public class Loan {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "loan_id")
	    private int loanId;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "type_of_loan", nullable = false)
	    @NotNull(message = "Type of loan cannot be null")
	    private LoanType typeOfLoan;

	    @Column(name = "interest_rate", nullable = false)
	    @NotNull(message = "Interest rate cannot be null")
	    private BigDecimal interestRate;

	    @Column(name = "term_months", nullable = false)
	    @Min(value = 5, message = "Term months must be at least 5")
	    @Max(value = 360, message = "Term months cannot be more than 360") 
	    private int termMonths;

	    @Column(name = "max_amount", nullable = false)
	    @NotNull(message = "Maximum amount cannot be null")
	    private BigDecimal maxAmount;

	    @Column(name = "min_amount", nullable = false)
	    @NotNull(message = "Minimum amount cannot be null")
	    private BigDecimal minAmount;
		public int getLoanId() {
			return loanId;
		}
		public void setLoanId(int loanId) {
			this.loanId = loanId;
		}
		public LoanType getTypeOfLoan() {
			return typeOfLoan;
		}
		public void setTypeOfLoan(LoanType typeOfLoan) {
			this.typeOfLoan = typeOfLoan;
		}
		public BigDecimal getInterestRate() {
			return interestRate;
		}
		public void setInterestRate(BigDecimal interestRate) {
			this.interestRate = interestRate;
		}
		public int getTermMonths() {
			return termMonths;
		}
		public void setTermMonths(int termMonths) {
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
		public Loan(int loanId, LoanType typeOfLoan, BigDecimal interestRate, int termMonths, BigDecimal maxAmount,
				BigDecimal minAmount) {
			super();
			this.loanId = loanId;
			this.typeOfLoan = typeOfLoan;
			this.interestRate = interestRate;
			this.termMonths = termMonths;
			this.maxAmount = maxAmount;
			this.minAmount = minAmount;
		}
		public Loan() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Loan [loanId=" + loanId + ", interestRate=" + interestRate + ", termMonths=" + termMonths
					+ ", maxAmount=" + maxAmount + ", minAmount=" + minAmount + "]";
		}
		

		public enum LoanType {
		    HOME, PERSONAL, STUDENT,BUSINESS
		}


		public boolean isValid() {
			// TODO Auto-generated method stub
			return false;
		}

	    
	    
}

