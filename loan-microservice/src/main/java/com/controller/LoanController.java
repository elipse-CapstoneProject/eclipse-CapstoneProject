package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.InvalidLoanTypeException;
import com.exception.LoanNotFoundException;
import com.google.common.base.Optional;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.service.LoanService;
@RestController
@Validated
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/type/{typeOfLoan}")
    public ResponseEntity<List<Loan>> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        Loan.LoanType loanType;
        try {
            loanType = Loan.LoanType.valueOf(typeOfLoan.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidLoanTypeException(typeOfLoan);
        }
        List<Loan> loans = loanService.getAllLoansByType(loanType);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/types")
    public ResponseEntity<List<Loan>> showAllTypeOfLoans() {
        List<Loan> loanList = loanService.listAllLoans();
        return ResponseEntity.ok(loanList);
    }

    @GetMapping("/id/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable("loanId") int loanId) {
        Loan loan = loanService.getLoanById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan ID " + loanId + " not found in the database"));
        return ResponseEntity.ok(loan);
    }
}
