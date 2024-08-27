package com.controller;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.exception.CustomException;
import com.exception.LoanNotFoundException;
import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.model.LoanApplicationStatus;
import com.model.Login;
import com.service.CustomerService;
import com.service.LoanApplicationStatusService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {
@Autowired
CustomerService customerService;
@Autowired
LoanApplicationStatusService loanApplicationStatusService;

private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


//For Register
@PostMapping
public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {

    boolean isPanCardNumberExists = customerService.existsByPanCardNumber(customer.getPanCardNumber());
    boolean isEmailExists = customerService.existsByEmail(customer.getEmail());
    if (isPanCardNumberExists) {
        return new ResponseEntity<>("Customer with the same registeration details already exists", HttpStatus.CONFLICT);
    }
    if (isEmailExists) {
        return new ResponseEntity<>("Customer with the same gesiteration details already exists", HttpStatus.CONFLICT);
    }
    Customer savedCustomer = customerService.addNewCustomer(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
}

//Login
@PostMapping("/login")
public ResponseEntity<?> loginCustomer(@Valid @RequestBody Login login) {
  if (login == null) {
      throw new IllegalArgumentException("Login request cannot be null");
  }
  if (login.getEmail() == null || login.getPassword() == null) {
      throw new IllegalArgumentException("Email and password must be provided");
  }
  String customer = customerService.authenticate(login.getEmail(), login.getPassword());
  if (customer != null) {
      return ResponseEntity.ok(customer);
  } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
  }
}

// retrieving based on typeofloan
@GetMapping("/type/{typeOfLoan}")
public ResponseEntity<?> getLoanType(@PathVariable("typeOfLoan") String typeOfLoan) {
    try {
        List<Loan> loans = customerService.getLoansByType(typeOfLoan);

        if (loans.isEmpty()) {
            return new ResponseEntity<>("No loan found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(loans, HttpStatus.OK);
        }
    } catch (Exception e) {
        return new ResponseEntity<>("Unexpected Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//retrieving all loans
@GetMapping("/types")
public ResponseEntity<?> getAllLoans() {
    try {
        List<Loan> loans = customerService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    } catch (LoanNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (RuntimeException e) {
        // Log the error
        return new ResponseEntity<>("Unexpected Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
//apply for loan
@PostMapping("/{customerId}/apply")
public ResponseEntity<String> applyForLoan(
        @PathVariable Long customerId,
        @RequestBody LoanApplicationRequest request) {

    Integer loanId = request.getLoanId();
    logger.info("Received loan application request for customerId: {} with loanId: {}", customerId, loanId);

    String result = loanApplicationStatusService.applyForLoan(customerId, loanId);

    if (result.contains("submitted successfully")) {
        logger.info("Loan application for customerId: {} with loanId: {} submitted successfully.", customerId, loanId);
        return ResponseEntity.ok(result);
    } else if (result.contains("not found")) {
        logger.warn("Loan with ID {} not found for customerId: {}", loanId, customerId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    } else {
        logger.error("Error occurred while applying for loan. Result: {}", result);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}


}


