package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.service.CustomerService;

@RestController
public class CustomerController {
@Autowired
CustomerService customerService;

}
