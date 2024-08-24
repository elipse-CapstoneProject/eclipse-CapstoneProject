package com.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.model.Customer;
import com.model.LoanApplicationStatus;

public interface LoanApplicationStatusRepository extends CrudRepository<LoanApplicationStatus, Long> {

}
