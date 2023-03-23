package com.yoursoft.redisapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoursoft.redisapp.domain.Customer;
import com.yoursoft.redisapp.dto.CustomerErrorReponse;
import com.yoursoft.redisapp.dto.CustomerRequest;
import com.yoursoft.redisapp.dto.CustomerResponse;
import com.yoursoft.redisapp.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Customer> getAll() {
        logger.info("CustomerController-> getAllCustomers ");
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> saveProduct(
            @RequestBody CustomerRequest customerRequest)
            throws JsonProcessingException {

        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable(value = "id") String id) {
        customerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer deleted successly");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") String id)
            throws JsonProcessingException {
        logger.info("CustomerController: Fetching Customer with id {}", id);
        Optional<CustomerResponse> customerResponse = customerService.findById(id);

        return customerResponse.isEmpty() ? ResponseEntity.ok().body(new CustomerErrorReponse("1-401","Cliente no encontrado"))
                : ResponseEntity.ok().body(customerResponse);
    }


}
