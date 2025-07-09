package com.secor.ecommcustomerservice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
@Getter
@Setter
public class Customer
{
    @Id
    private String customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    List<String> orders; // List of order IDs associated with the customer
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}