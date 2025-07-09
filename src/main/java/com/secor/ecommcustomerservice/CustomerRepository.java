package com.secor.ecommcustomerservice;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String>
{
    Customer findByCustomerId(String customerId);
}
