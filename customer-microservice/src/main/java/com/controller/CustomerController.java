package com.controller;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.exception.CustomException;
import com.model.Customer;
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
@PostMapping
public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
	System.out.println(customer.getCustomerName());
    Customer savedCustomer = customerService.addNewCustomer(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.OK); 
}
//Login
@PostMapping("/login")
public ResponseEntity<?> loginCustomer(@Valid @RequestBody Login login) {
	System.out.println("customer is calling");
    Customer customer = customerService.authenticate(login.getEmail(), login.getPassword());
    System.out.println(customer.getEmail());
    if (customer != null) {
        return ResponseEntity.ok(customer);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
private final String baseUrl = "http://loanservice/loans";
/*@GetMapping("/loans/{type_of_loan}")
public ResponseEntity<?> getUserDetails(@PathVariable("type_of_loan") LoanType loanType) {
    String url = baseUrl + "/" + loanType;

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
}*/
@GetMapping("/loan-options")
public ResponseEntity<String> getAvailableLoanOptions() {
    // Fetch available loan options from Loan microservice
    String loanOptions = restTemplate.getForObject(baseUrl, String.class);
    return ResponseEntity.ok(loanOptions);
}
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
}
}





