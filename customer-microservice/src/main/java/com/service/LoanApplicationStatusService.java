package com.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.exception.LoanNotFoundException;
import com.model.Loan;
import com.model.LoanApplicationStatus;
import com.repository.LoanApplicationStatusRepository;

@Service
public class LoanApplicationStatusService {
@Autowired
LoanApplicationStatusRepository loanApplicationStatusRepository;
@Autowired
RestTemplate restTemplate;
private  String baseUrl = "http://loanservice/loans";
private static final Logger logger = LoggerFactory.getLogger(LoanApplicationStatusService.class);

public LoanApplicationStatusService(RestTemplate restTemplate, LoanApplicationStatusRepository loanApplicationStatusRepository) {
    this.restTemplate = restTemplate;
    this.loanApplicationStatusRepository = loanApplicationStatusRepository;
}

public String applyForLoan(Long customerId, Integer loanId) {
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
            logger.warn("Loan with ID {} not found.", loanId);
            return "Loan with ID " + loanId + " not found.";
        }
    } catch (HttpClientErrorException.NotFound e) {
        logger.warn("Loan with ID {} not found: {}", loanId, e.getMessage());
        return "Loan with ID " + loanId + " not found.";
    } catch (Exception e) {
        logger.error("An error occurred while applying for the loan: {}", e.getMessage(), e);
        return "An error occurred while applying for the loan: " + e.getMessage();
    }
}

}
