package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.LoanApplicationStatus;
@Repository
public interface LoanApplicationStatusRepository extends CrudRepository<LoanApplicationStatus, Integer> {
}
