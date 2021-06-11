package com.example.authservice.service;

import com.example.authservice.dao.entities.Customer;

public interface AuthService {

     Object authorizeCustomer(String requestBody);

    Object createCustomer(Customer customer);
}
