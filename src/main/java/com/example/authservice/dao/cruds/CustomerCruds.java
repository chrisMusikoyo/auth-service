package com.example.authservice.dao.cruds;


import com.example.authservice.dao.entities.Customer;

public interface CustomerCruds {

    Customer createCustomer(Customer customer);
    Customer findCustomer(long id);
    Customer findCustomer(String email);
}
