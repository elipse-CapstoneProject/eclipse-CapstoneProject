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

public boolean existsByPanCardNumber(String panCardNumber) {
    boolean exists = customerRepository.existsByPanCardNumber(panCardNumber);
    logger.debug("Check if PAN Card Number {} exists: {}", panCardNumber, exists);
    return exists;
}

public boolean existsByEmail(String email) {
    boolean exists = customerRepository.existsByEmail(email);
    logger.debug("Check if Email {} exists: {}", email, exists);
    return exists;
}

public Customer addNewCustomer(@Valid Customer customer) {
    logger.debug("Adding new customer with Email: {}", customer.getEmail());
    return customerRepository.save(customer);
}

public String authenticate(String email, String password) {
    logger.debug("Authenticating user with Email: {}", email);
    Customer customer = customerRepository.findByEmail(email);
    if (customer != null && customer.getPassword().equals(password)) {
        logger.info("Authentication successful for Email: {}", email);
        return "login successful";
    }
    logger.warn("Authentication failed for Email: {}", email);
    return null;
}

public List<Loan> getLoansByType(String typeOfLoan) {
    String typeOfLoanUpperCase = typeOfLoan.toUpperCase();
    String url = baseUrl + "/type/" + typeOfLoanUpperCase;
    logger.debug("Fetching loans from URL: {}", url);
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
            logger.info("Loans retrieved successfully for type: {}", typeOfLoanUpperCase);
            return response.getBody();
        } else {
            throw new IllegalStateException("Invalid type" + response.getStatusCode());
        }
    } catch (LoanNotFoundException e) {
        logger.warn("No loans found for type: {}", typeOfLoanUpperCase);
        throw e;
    } catch (Exception e) {
        logger.error("Error occurred while fetching loans for type: {}", typeOfLoanUpperCase, e);
        throw new RuntimeException("Error occurred while fetching loans for type " + typeOfLoanUpperCase, e);
    }
}

public List<Loan> getAllLoans() {
    String url = baseUrl + "/types";
    logger.debug("Fetching all loans from URL: {}", url);
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
            logger.info("All loans retrieved successfully");
            return response.getBody();
        } else {
            throw new IllegalStateException("Unexpected status code " + response.getStatusCode());
        }
    } catch (RestClientException e) {
        logger.error("Error fetching loans: {}", e.getMessage(), e);
        throw new RuntimeException("Error fetching loans: " + e.getMessage(), e);
    }
}
}











