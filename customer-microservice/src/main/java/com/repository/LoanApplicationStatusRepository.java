package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.LoanApplicationStatus;
@Repository
public interface LoanApplicationStatusRepository extends CrudRepository<LoanApplicationStatus, Integer> {

	static boolean existsById(Long customerId) {
		// TODO Auto-generated method stub
		return false;
	}

Optional<LoanApplicationStatus> findByApplicationId(Long applicationId);
List<LoanApplicationStatus> findByCustomerId(Long customerId);
}
