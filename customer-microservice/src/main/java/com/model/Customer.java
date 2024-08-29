package com.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Entity
@Table(name="customer")
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long customerId;

    @Column(name ="customer_name", nullable=false)
    @NotBlank(message = "Customer Name cannot be blank")
    private String customerName;

    @Column(name = "email")
    @Email(message = "Email should be valid format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Column(name = "date_of_birth")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @Column(name = "password")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8,max=12, message = "Password should be at least 8 characters long")
    private String password;

    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phoneNumber;

    @Column(name = "pan_card_number")
    @Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]{1}", message = "PAN card number should be in Correct format")
    private String panCardNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    @NotBlank(message = "Address cannot be blank")
    private String address;
	  

		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public @Past(message = "Date of birth must be in the past") LocalDate getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(@Past(message = "Date of birth must be in the past") LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getPanCardNumber() {
			return panCardNumber;
		}

		public void setPanCardNumber(String panCardNumber) {
			this.panCardNumber = panCardNumber;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

	

		public Customer( String customerName, String email, @Past(message = "Date of birth must be in the past") LocalDate dateOfBirth, String gender,
				String password, String phoneNumber, String panCardNumber, String address,
				List<LoanApplicationStatus> loanstatus) {
			super();
			this.customerName = customerName;
			this.email = email;
			this.dateOfBirth = dateOfBirth;
			this.gender = gender;
			this.password = password;
			this.phoneNumber = phoneNumber;
			this.panCardNumber = panCardNumber;
			this.address = address;
		}

		public Customer() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", email=" + email
					+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", password=" + password
					+ ", phoneNumber=" + phoneNumber + ", panCardNumber=" + panCardNumber + ", address=" + address
					+ "]";
		}
	
		
		
	    
}
