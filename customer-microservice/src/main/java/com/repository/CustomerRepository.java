package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
   
	Customer findByEmail(String email);

	//Optional<Customer> findById(Long customerId);
	
    boolean existsByEmail(String email);

	




}