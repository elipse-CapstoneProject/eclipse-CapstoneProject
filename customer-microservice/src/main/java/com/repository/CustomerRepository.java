package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByEmail(String email);


}
