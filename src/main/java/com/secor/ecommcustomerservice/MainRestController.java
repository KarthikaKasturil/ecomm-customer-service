package com.secor.ecommcustomerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class MainRestController {

    private final Logger LOG = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        LOG.info("getAllCustomers");
        return customerRepository.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        LOG.info("getCustomerById({})", customerId);
        return customerRepository.findByCustomerId(customerId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        LOG.info("addCustomer({})", customer);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setOrders(new ArrayList<>());
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok("Customer " + savedCustomer.getFirstName() + " saved");
    }

    @PutMapping("/update/details")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customerDetails) {
        LOG.info("updateCustomer({})", customerDetails);
        Optional<Customer> customer = customerRepository.findById(customerDetails.getCustomerId());
        if (customer.isEmpty()) {
            return ResponseEntity.status(400).body("No such customer");
        }
        Customer existingCustomer = customer.get();
        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        existingCustomer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(existingCustomer);
        return ResponseEntity.ok("Updated details of  " + savedCustomer.getFirstName());
    }

    @PutMapping("/update/order")
    public ResponseEntity<?> updateOrderForCustomer(@RequestBody OrderInfo orderInfo) {
        LOG.info("updateOrderForCustomer({})", orderInfo);
        Optional<Customer> customerOpt = customerRepository.findById(orderInfo.getCustomerId());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.getOrders().add(orderInfo.getOrderId());
            customer.setUpdatedAt(LocalDateTime.now());
            customerRepository.save(customer);
            LOG.info("Updated order for customer: " + customer.getFirstName());
            return ResponseEntity.ok("Updated order for customer");
        }
        else {
            LOG.error("No customer found with ID: " + orderInfo.getCustomerId());
            return ResponseEntity.status(400).body("No such customer");
        }
    }

}