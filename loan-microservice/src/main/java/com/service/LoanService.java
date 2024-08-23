package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Loan;
import com.model.Loan.LoanType;
import com.repository.LoanRepository;

@Service
public class LoanService {
@Autowired
LoanRepository loanRepository;

public List<Loan> getAllLoansByType(LoanType typeOfLoan) {
    return loanRepository.findByTypeOfLoan(typeOfLoan);
}

public Optional<Loan> getLoanById(int loanId) {
    return loanRepository.findById(loanId);
}

public List<Loan> listAllLoans() {
	
	return loanRepository.findAll();
}
}
