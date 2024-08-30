package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.model.LoanApplicationStatus;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
   
	 Customer findByEmail(String email);
    //register
    boolean existsByPanCardNumber(String panCardNumber);
    boolean existsByEmail(String email);

	void save(LoanApplicationStatus loanApplication);

	boolean existsByCustomerId(Long customerId);
	


}