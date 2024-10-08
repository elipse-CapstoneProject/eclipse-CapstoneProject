package com.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exception.LoanNotFoundException;
import com.model.Customer;
import com.model.Loan;
import com.model.LoanApplicationStatus;
import com.model.Login;
import com.service.CustomerService;
import com.service.LoanApplicationStatusService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    LoanApplicationStatusService loanApplicationStatusService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    // Register
    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        logger.debug("Registering customer with PAN: {} and Email: {}", customer.getPanCardNumber(), customer.getEmail());
        boolean isPanCardNumberExists = customerService.existsByPanCardNumber(customer.getPanCardNumber());
        boolean isEmailExists = customerService.existsByEmail(customer.getEmail());
        if (isPanCardNumberExists) {
            logger.warn("Customer with PAN Card Number {} already exists", customer.getPanCardNumber());
            return new ResponseEntity<>("Customer with the same registration details already exists", HttpStatus.CONFLICT);}
        if (isEmailExists) {
            logger.warn("Customer with Email {} already exists", customer.getEmail());
            return new ResponseEntity<>("Customer with the same registration details already exists", HttpStatus.CONFLICT);}
        Customer savedCustomer = customerService.addNewCustomer(customer);
        logger.info("Customer registered successfully with ID: {}", savedCustomer.getCustomerId());
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED); }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@Valid @RequestBody Login login) {
        logger.debug("Login attempt for Email: {}", login.getEmail());
        if (login == null || login.getEmail() == null || login.getPassword() == null) {
            logger.error("Invalid login request");
            throw new IllegalArgumentException("Email and password must be provided");}
        String customer = customerService.authenticate(login.getEmail(), login.getPassword());
        if (customer != null) {
            logger.info("Login successful for Email: {}", login.getEmail());
            return ResponseEntity.ok(customer);} 
        else {
            logger.warn("Invalid email or password for Email: {}", login.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");}
    }
    // Retrieve loans based on type of loan
    @GetMapping("/type/{typeOfLoan}")
    public ResponseEntity<?> getLoanType(@PathVariable("typeOfLoan") String typeOfLoan) {
        logger.debug("Retrieving loans for type: {}", typeOfLoan);
        try {
            List<Loan> loans = customerService.getLoansByType(typeOfLoan);
            if (loans.isEmpty()) {
                logger.info("No loans found for type: {}", typeOfLoan);
                return new ResponseEntity<>("No loan found", HttpStatus.NOT_FOUND);
            } else {
                logger.info("Loans retrieved successfully for type: {}", typeOfLoan);
                return new ResponseEntity<>(loans, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving loans for type: {}", typeOfLoan, e);
            String errorMessage = String.format("No loan found for type: %s", typeOfLoan);
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Retrieve all loans
    @GetMapping("/types")
    public ResponseEntity<?> getAllLoans() {
        logger.debug("Retrieving all loans");
        try {
            List<Loan> loans = customerService.getAllLoans();
            logger.info("All loans retrieved successfully");
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (LoanNotFoundException e) {
            logger.warn("No loans found: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            logger.error("Unexpected error while retrieving all loans: {}", e.getMessage(), e);
            return new ResponseEntity<>("Unexpected Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Apply for loan
    @PostMapping("/{customerId}/apply")
    public ResponseEntity<String> applyForLoan(
            @PathVariable Long customerId,
            @RequestBody LoanApplicationRequest request) {
        logger.debug("Received loan application request for customerId: {} with loanId: {}", customerId, request.getLoanId());
        String result = loanApplicationStatusService.applyForLoan(customerId, request.getLoanId());
        if (result.contains("submitted successfully")) {
            logger.info("Loan application for customerId: {} with loanId: {} submitted successfully.", customerId, request.getLoanId());
            return ResponseEntity.ok(result);
        } else if (result.contains("not found")) {
            logger.warn("Loan with ID {} not found for customerId: {}", request.getLoanId(), customerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            logger.error("Error occurred while applying for loan. Result: {}", result);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        } }
    //retrieving customers application details
    @GetMapping("/{customerId}/applications")
    public ResponseEntity<?> getApplicationsByCustomerId(@PathVariable Long customerId) {
        logger.info("Received request to get loan applications for customer ID: {}", customerId);
        if (customerId == null || customerId <= 0) {
            logger.warn("Invalid customer ID provided: {}", customerId);
            return ResponseEntity.badRequest().body("Invalid customer ID provided.");}
        try {
            List<LoanApplicationStatus> applications = loanApplicationStatusService.getApplicationsByCustomerId(customerId);
            if (applications.isEmpty()) {
                logger.warn("No loan applications found for customer ID: {}", customerId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No loan applications found for customer ID " + customerId + "."); }
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loan application data for customer ID: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while retrieving loan application data: " + e.getMessage());
        }}}