package com.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.model.Customer;
import com.model.Loan;
import com.model.Loan.LoanType;
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
//For Register
/*@PostMapping
public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
	System.out.println(customer.getCustomerName());
    Customer savedCustomer = customerService.addNewCustomer(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.OK); 
}*/

@PostMapping
public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
    System.out.println(customer.getCustomerName());

    // Check if a customer with the same PAN card number or email already exists
    boolean isPanCardNumberExists = customerService.existsByPanCardNumber(customer.getPanCardNumber());
    boolean isEmailExists = customerService.existsByEmail(customer.getEmail());

    if (isPanCardNumberExists) {
        return new ResponseEntity<>("Customer with the same PAN card number already exists", HttpStatus.CONFLICT);
    }

    if (isEmailExists) {
        return new ResponseEntity<>("Customer with the same email address already exists", HttpStatus.CONFLICT);
    }

    // Save the new customer if no duplicates are found
    Customer savedCustomer = customerService.addNewCustomer(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
}


//Login
@PostMapping("/login")
public ResponseEntity<?> loginCustomer(@Valid @RequestBody Login login) {
    System.out.println("Customer is calling");

    // Check if the login object is null or has null fields
    if (login == null) {
        throw new IllegalArgumentException("Login request cannot be null");
    }

    if (login.getEmail() == null || login.getPassword() == null) {
        throw new IllegalArgumentException("Email and password must be provided");
    }

    // Authenticate the customer
    Customer customer = customerService.authenticate(login.getEmail(), login.getPassword());

    // Check if the customer was authenticated successfully
    if (customer != null) {
        return ResponseEntity.ok(customer);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}


private  String baseUrl = "http://loanservice/loans";
// retrieving based on typeofloan
@GetMapping("/loans/{typeOfLoan}")
public ResponseEntity<?> getUserDetails(@PathVariable("typeOfLoan") LoanType typeOfLoan) {
    String url = baseUrl + "/" + typeOfLoan; 
    try {
        ResponseEntity<Loan> response = restTemplate.getForEntity(url, Loan.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            if (response.getBody() == null) {
                return new ResponseEntity<String>("No loan record found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unexpected error occurred", response.getStatusCode());
        }
    } catch (RestClientException e) {
        return new ResponseEntity<String>("No loan found by this type of loan: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


//applying for loan
/*@PostMapping("/{customerId}/loan-applications")
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
@GetMapping("/loans")
public ResponseEntity<?> getAllLoans() {
    String url = baseUrl + "/all"; 

    try {
        ResponseEntity<List<Loan>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Loan>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            if (response.getBody() == null || response.getBody().isEmpty()) {
                return new ResponseEntity<String>("No loan records found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unexpected error occurred", response.getStatusCode());
        }
    } catch (RestClientException e) {
        return new ResponseEntity<String>("Error fetching loans: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}