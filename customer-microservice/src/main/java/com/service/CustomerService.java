package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.controller.LoanApplicationRequest;
import com.model.Customer;
import com.model.LoanApplicationStatus;
import com.repository.CustomerRepository;
import com.repository.LoanApplicationStatusRepository;

import jakarta.validation.Valid;

@Service
public class CustomerService {
@Autowired
CustomerRepository customerRepository;
@Autowired
LoanApplicationStatusRepository loanApplicationStatusRepository;

 //register
public boolean existsByPanCardNumber(String panCardNumber) {
    return customerRepository.existsByPanCardNumber(panCardNumber);
}

public boolean existsByEmail(String email) {
    return customerRepository.existsByEmail(email);
}

public Customer addNewCustomer(@Valid Customer customer) {
	 return customerRepository.save(customer);}


//login
public String authenticate(String email, String password) {
	 Customer customer = customerRepository.findByEmail(email);
	    if (customer != null && customer.getPassword().equals(password)) {
	        return ("login successful");
	    }
	    return null;
}



/*
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
*/

//apply for loan

public void applyForLoan(Long customerId, LoanApplicationStatus applicationStatus) {
    // Save the loan application status
    loanApplicationStatusRepository.save(applicationStatus);
}
}






