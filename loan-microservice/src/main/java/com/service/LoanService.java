package com.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Loan;
import com.model.Loan.LoanType;
import com.repository.LoanRepository;
@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);
    //retrieving based on typeofloan
    public List<Loan> getAllLoansByType(Loan.LoanType typeOfLoan) {
        logger.info("Fetching all loans of type: {}", typeOfLoan);
        List<Loan> loans = loanRepository.findByTypeOfLoan(typeOfLoan);
        logger.info("Found {} loans of type: {}", loans.size(), typeOfLoan);
        return loans;
    }
 // Retrieving all loans
    public List<Loan> listAllLoans() {
        logger.info("Fetching all loans from the database");
        List<Loan> loans = (List<Loan>) loanRepository.findAll();
        logger.info("Found {} loans in total", loans.size());
        return loans;
    }
  //retrieving based on id
    public Optional<Loan> getLoanById(int loanId) {
        logger.info("Searching for loan with ID: {}", loanId);
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            logger.info("Loan with ID {} found", loanId);
        } else {
            logger.warn("Loan with ID {} not found", loanId);
        }
        return loan;
    }
}

   
