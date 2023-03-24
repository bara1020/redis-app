package com.yoursoft.redisapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yoursoft.redisapp.domain.Customer;
import com.yoursoft.redisapp.dto.CustomerRequest;
import com.yoursoft.redisapp.dto.CustomerResponse;
import com.yoursoft.redisapp.exceptions.ResourceNotFoundException;
import com.yoursoft.redisapp.repository.CustomerRepository;
import com.yoursoft.redisapp.service.CustomerService;
import com.yoursoft.redisapp.service.RedisUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RedisUtility redisUtility;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Optional<CustomerResponse> findById(String id)
            throws JsonProcessingException {

        Customer customer = redisUtility.getValue(id);

        if(customer != null){
            return Optional.of(modelMapper.map(customer, CustomerResponse.class));
        }

        Optional<Customer> customerDbOptional = customerRepository.findById(id);
        if(customerDbOptional.isPresent()){
            return customerDbOptional.map(customerDb -> modelMapper.map(customerDb, CustomerResponse.class));
        }

        throw new ResourceNotFoundException("Customer", "id", id);
    }

    @Override
    public CustomerResponse save(CustomerRequest customerRequest) throws JsonProcessingException {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        Customer customerSaved = null;

        customerSaved = redisUtility.getValue(customerRequest.getId());

        if(customerSaved == null){
            customerSaved = customerRepository.save(customer);
            if(customerSaved != null)
                redisUtility.setValue(customer.getId(), customerSaved);
        }

        return modelMapper.map(customerSaved, CustomerResponse.class);
    }

    @Override
    public void deleteById(String id) throws JsonProcessingException {

        if(redisUtility.getValue(id) != null) {
            deleteItem(id);
        } else {
            Optional<Customer> customer = customerRepository.findById(id);
            if(customer.isEmpty())
                throw new ResourceNotFoundException("Customer", "id", id);
        }
    }

    private void deleteItem (String id){
        customerRepository.deleteById(id);
        redisUtility.delete(id);
    }
}
