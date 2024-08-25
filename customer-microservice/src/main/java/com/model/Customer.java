package com.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="customer")
public class Customer {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="customer_id")
	    private Long customerId;

	    @Column(name ="customer_name", nullable=false)
	    private String customerName;

	    @Column(name = "email")
	    private String email;

	    @Column(name = "date_of_birth")
	    private String dateOfBirth;

	    @Column(name = "gender")
	    private String gender;

	    @Column(name = "password")
	    private String password;

	    @Column(name = "phone_number")
	    private String phoneNumber;

	    @Column(name = "pan_card_number")
	    private String panCardNumber;

	    @Column(name = "address", columnDefinition = "TEXT")
	    private String address;
	    
	 /*   @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	    private List<LoanApplicationStatus> loanApplications;
*/
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

		public String getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(String dateOfBirth) {
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

	/*	public List<LoanApplicationStatus> getLoanApplications() {
			return loanApplications;
		}

		public void setLoanApplications(List<LoanApplicationStatus> loanApplications) {
			this.loanApplications = loanApplications;
		}*/

		public Customer(String customerName, String email, String dateOfBirth, String gender,
				String password, String phoneNumber, String panCardNumber, String address,
				List<LoanApplicationStatus> loanApplications) {
			super();
			
			this.customerName = customerName;
			this.email = email;
			this.dateOfBirth = dateOfBirth;
			this.gender = gender;
			this.password = password;
			this.phoneNumber = phoneNumber;
			this.panCardNumber = panCardNumber;
			this.address = address;
			//this.loanApplications = loanApplications;
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

		public Object getLoanApplications() {
			// TODO Auto-generated method stub
			return null;
		}

		
	    
}
