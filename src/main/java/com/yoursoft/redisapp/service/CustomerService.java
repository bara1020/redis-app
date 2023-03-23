package com.yoursoft.redisapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoursoft.redisapp.domain.Customer;
import com.yoursoft.redisapp.dto.CustomerRequest;
import com.yoursoft.redisapp.dto.CustomerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {


    void deleteById(String id);
    CustomerResponse save(CustomerRequest customerRequest) throws JsonProcessingException;
    List<Customer> findAll();
    Optional<CustomerResponse> findById(String id) throws JsonProcessingException;
}
