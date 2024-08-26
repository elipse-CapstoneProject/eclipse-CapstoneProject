package com.controller;
import java.sql.Timestamp;
import java.util.List;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.exception.CustomException;
import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
import com.model.LoanApplicationStatus;
import com.model.Login;
import com.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {
@Autowired
CustomerService customerService;
@Autowired
RestTemplate restTemplate;

private  String baseUrl = "http://loanservice/loans";


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
public ResponseEntity<?> getLoanType(@PathVariable("typeOfLoan") LoanType typeOfLoan) {
    String url = baseUrl + "/type/" + typeOfLoan;
    System.out.println(url);
    ResponseEntity<List<Loan>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Loan>>() {}
    );

    if (response.getStatusCode() == HttpStatus.NO_CONTENT || response.getBody().isEmpty()) {
        System.out.println("coming");
        return new ResponseEntity<>("No loan found", HttpStatus.NOT_FOUND);
    } else if (response.getStatusCode() == HttpStatus.OK) {
        return response;
    }
    return new ResponseEntity<>("Unexpected Error", HttpStatus.INTERNAL_SERVER_ERROR);
}



/*
//applying for loan
@PostMapping("/{customerId}/loan-applications")
public ResponseEntity<Customer> applyForLoan(
        @PathVariable("customerId") Long customerId,
        @RequestParam Integer loanId) {
    try {
        Customer customer = customerService.applyForLoan(customerId, loanId);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    } catch (CustomException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
}

@ExceptionHandler(CustomException.class)
public ResponseEntity<String> handleCustomerNotFoundException(CustomException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
}*/


//retrieving all loans
@GetMapping("/types")
public ResponseEntity<?> getAllLoans() {
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
                return new ResponseEntity<>("No loan records found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unexpected error occurred", response.getStatusCode());
        }
    } catch (RestClientException e) {
        return new ResponseEntity<>("Error fetching loans: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
//apply for loan
@PostMapping("/{customerId}/apply")
public ResponseEntity<String> applyForLoan(
        @PathVariable Long customerId,
        @RequestBody LoanApplicationRequest request) {
    Integer loanId = request.getLoanId();
    String url = baseUrl + "/" + loanId;

    try {
        ResponseEntity<Loan> response = restTemplate.getForEntity(url, Loan.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LoanApplicationStatus applicationStatus = new LoanApplicationStatus();
            applicationStatus.setCustomerId(customerId);
            applicationStatus.setLoanId(loanId);
            applicationStatus.setApplicationDate(new Timestamp(System.currentTimeMillis()));
            applicationStatus.setStatus(LoanApplicationStatus.Status.PENDING);

            customerService.applyForLoan(customerId, applicationStatus);
            return ResponseEntity.ok("Loan application submitted successfully.");
        } else {
            // Loan not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Loan with ID " + loanId + " not found.");
        }
    } catch (HttpClientErrorException.NotFound e) {
        // Handle case where the loan ID is not found
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Loan with ID " + loanId + " not found.");
    } catch (Exception e) {
        // Handle other exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("An error occurred while applying for the loan: " + e.getMessage());
    }
}

}



