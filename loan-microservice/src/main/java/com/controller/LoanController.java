package com.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exception.InvalidLoanTypeException;
import com.exception.LoanNotFoundException;
import com.model.Loan;
import com.service.LoanService;
@RestController
@Validated
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    @GetMapping("/type/{typeOfLoan}")
    //retrieving based on typeofloan
    public ResponseEntity<List<Loan>> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        Loan.LoanType loanType;
        try {
            loanType = Loan.LoanType.valueOf(typeOfLoan.toUpperCase());
            logger.info("Fetching loans for type: {}", loanType);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid loan type received: {}", typeOfLoan, e);
            throw new InvalidLoanTypeException(typeOfLoan);
        }
        List<Loan> loans = loanService.getAllLoansByType(loanType);
        logger.info("Found {} loans for type: {}", loans.size(), loanType);
        return ResponseEntity.ok(loans);
    }
// Retrieving all loans
    @GetMapping("/types")
    public ResponseEntity<List<Loan>> showAllTypeOfLoans() {
        List<Loan> loanList = loanService.listAllLoans();
        logger.info("Retrieved {} loans from the database", loanList.size());
        return ResponseEntity.ok(loanList);
    }
//retrieving based on id
    @GetMapping("/id/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable("loanId") int loanId) {
        java.util.Optional<Loan> loanOptional = loanService.getLoanById(loanId);
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            logger.info("Found loan with ID: {}", loanId);
            return ResponseEntity.ok(loan);
        } else {
            logger.error("Loan ID {} not found in the database", loanId);
            throw new LoanNotFoundException("Loan ID " + loanId + " not found in the database");
        }
    }
}
