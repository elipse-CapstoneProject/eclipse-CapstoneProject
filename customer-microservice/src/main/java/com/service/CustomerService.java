package com.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.controller.LoanApplicationRequest;
import com.exception.LoanNotFoundException;
import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
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
@Autowired
RestTemplate restTemplate;
private  String baseUrl = "http://loanservice/loans";
private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

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
//retrieving typeofloan
public List<Loan> getLoansByType(String typeOfLoan) {
    String typeOfLoanUpperCase = typeOfLoan.toUpperCase();
    String url = baseUrl + "/type/" + typeOfLoanUpperCase;
    try {
        ResponseEntity<List<Loan>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Loan>>() {}
        );
        if (response.getStatusCode() == HttpStatus.NO_CONTENT || response.getBody().isEmpty()) {
            throw new LoanNotFoundException("No loans found for type: " + typeOfLoanUpperCase);
        } else if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new IllegalStateException("Invalid type" + response.getStatusCode());
        }
    } catch (LoanNotFoundException e) {
        throw e; 
    } catch (Exception e) {
        throw new RuntimeException("Error occurred while fetching loans for type " + typeOfLoanUpperCase, e);
    }
}

//retrieving all loans

public List<Loan> getAllLoans() {
    String url = baseUrl + "/types";
    try {
        ResponseEntity<List<Loan>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Loan>>() {}
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            if (response.getBody() == null || response.getBody().isEmpty()) {
                throw new LoanNotFoundException("No loan records found");
            }
            return response.getBody();
        } else {
            throw new IllegalStateException("Unexpected status code " + response.getStatusCode());
        }
    } catch (RestClientException e) {
        throw new RuntimeException("Error fetching loans: " + e.getMessage(), e);
    }
}
}











