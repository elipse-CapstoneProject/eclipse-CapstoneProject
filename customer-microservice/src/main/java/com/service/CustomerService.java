package com.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.CustomException;
import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.model.LoanApplicationStatus;
import com.repository.CustomerRepository;
import com.repository.LoanApplicationStatusRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CustomerService {
@Autowired
CustomerRepository customerRepository;
@Autowired
LoanApplicationStatusRepository loanApplicationStatusRepository;
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

/*public Iterable<Customer> listAllLoans() {
	
	return customerRepository.findAll();
}


public boolean existsById(Long customerId) {
	// TODO Auto-generated method stub
	return false;
}*/

@Transactional
public Customer applyForLoan(Long customerId, Integer loanId) throws CustomException {
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException("Customer with ID " + customerId + " not found"));

    LoanApplicationStatus application = new LoanApplicationStatus();
    application.setCustomer(customer);
    application.setLoanId(loanId);
    application.setApplicationDate(new Timestamp(System.currentTimeMillis()));
    application.setStatus(LoanApplicationStatus.Status.PENDING);

    loanApplicationStatusRepository.save(application);

    return customer;
}

}
