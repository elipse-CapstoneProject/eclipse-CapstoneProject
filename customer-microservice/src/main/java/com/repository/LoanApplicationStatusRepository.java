package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.model.LoanApplicationStatus;

public interface LoanApplicationStatusRepository extends CrudRepository<LoanApplicationStatus, Long> {
	//List<LoanApplicationStatus> findByCustomerId(Long customerId);
}
