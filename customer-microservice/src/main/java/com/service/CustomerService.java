package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.repository.CustomerRepository;

import jakarta.validation.Valid;

@Service
public class CustomerService {
@Autowired
CustomerRepository customerRepository;

public Customer addNewCustomer(Customer customer) {
	Customer customer1=customerRepository.save(customer);
	return customer1;
}


public Customer authenticate(String email, String password) {
	 Customer customer = customerRepository.findByEmail(email);
	    if (customer != null && customer.getPassword().equals(password)) {
	        return customer;
	    }
	    return null;
}

public Iterable<Customer> listAllLoans() {
	
	return customerRepository.findAll();
}


}
