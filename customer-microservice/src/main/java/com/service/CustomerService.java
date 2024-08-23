package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.CustomerRepository;

@Service
public class CustomerService {
@Autowired
CustomerRepository customerRepository;
}
