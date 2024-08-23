package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Loan;
import com.model.Loan.LoanType;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer>{
   

	List<Loan> findByTypeOfLoan(LoanType typeOfLoan);

	List<Loan> findAll();

}
