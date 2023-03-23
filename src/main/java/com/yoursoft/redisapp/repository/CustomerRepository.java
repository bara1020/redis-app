package com.yoursoft.redisapp.repository;

import com.yoursoft.redisapp.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
}
