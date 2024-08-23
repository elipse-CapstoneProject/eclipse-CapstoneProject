package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.service.LoanService;

@RestController
@Validated
@RequestMapping("/loans")
public class LoanController {
@Autowired
LoanService loanService;

/*@GetMapping("/type/{typeOfLoan}")
public ResponseEntity<List<Loan>> getLoansByType(@PathVariable("typeOfLoan") LoanType typeOfLoan) {
    List<Loan> loans = loanService.getAllLoansByType(typeOfLoan);
    return ResponseEntity.ok(loans);
}*/

@GetMapping("/types")
public List<Loan> showAllTypeOfLoans() {
	List<Loan> loanList=loanService.listAllLoans();
	return loanList;
}


@GetMapping("/id/{loanId}")
public Loan getLoanById(@PathVariable("loanId") int loanId) {
    java.util.Optional<Loan> loan = loanService.getLoanById(loanId);
    return loan.orElse(null);
}
}


