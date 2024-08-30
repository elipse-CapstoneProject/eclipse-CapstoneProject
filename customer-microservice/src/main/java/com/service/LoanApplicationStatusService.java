package com.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.model.Loan;
import com.model.LoanApplicationStatus;
import com.repository.CustomerRepository;
import com.repository.LoanApplicationStatusRepository;

@Service
public class LoanApplicationStatusService {
@Autowired
CustomerRepository customerRepository;
@Autowired
CustomerService customerService;
@Autowired
LoanApplicationStatusRepository loanApplicationStatusRepository;
@Autowired
RestTemplate restTemplate;
private  String baseUrl = "http://loanservice/loans";
private static final Logger logger = LoggerFactory.getLogger(LoanApplicationStatusService.class);
//apply for loan
public String applyForLoan(Long customerId, Integer loanId) {
    if (!customerRepository.existsByCustomerId(customerId)) {
        logger.warn("Customer with ID {} not found", customerId);
        return "Customer with ID " + customerId + " not found.";
    }
    String loanUrl = baseUrl + "/id/" + loanId;
    logger.debug("Loan URL is: {}", loanUrl);
    try {
        ResponseEntity<Loan> response = restTemplate.getForEntity(loanUrl, Loan.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            LoanApplicationStatus loanApplicationStatus = new LoanApplicationStatus();
            loanApplicationStatus.setCustomerId(customerId);
            loanApplicationStatus.setLoanId(loanId);
            loanApplicationStatus.setApplicationDate(new Timestamp(System.currentTimeMillis()));
            loanApplicationStatus.setStatus(LoanApplicationStatus.Status.PENDING);
            loanApplicationStatusRepository.save(loanApplicationStatus);
            logger.info("Loan application for customerId: {} with loanId: {} submitted successfully.", customerId, loanId);
            return "Loan application submitted successfully.";
        } else {
            logger.warn("Loan with ID {} not found", loanId);
            return "Loan with ID " + loanId + " not found.";
        }
    } catch (Exception e) {
        logger.error("An error occurred while applying for the loan: {}", e.getMessage(), e);
        return "An error occurred while applying for the loan: " + e.getMessage();
    }
}
public List<LoanApplicationStatus> getApplicationsByCustomerId(Long customerId) {
    if (customerId == null) {
        logger.error("Customer ID must not be null");
        throw new IllegalArgumentException("Customer ID must not be null");
    }
    logger.info("Fetching loan applications for customer ID: {}", customerId);
    List<LoanApplicationStatus> loanApplications = loanApplicationStatusRepository.findByCustomerId(customerId);
    if (loanApplications.isEmpty()) {
        logger.warn("No loan applications found for customer ID: {}", customerId);
    } else {
        logger.info("Loan applications found: {}", loanApplications);
    }
    return loanApplications;  
}
}
