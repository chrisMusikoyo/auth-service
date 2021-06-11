package com.example.authservice.dao.cruds;

import com.example.authservice.dao.entities.Customer;
import com.example.authservice.dao.repo.CustomerRepo;
import com.example.authservice.exceptions.CustomerNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCrudsImpl implements CustomerCruds {

    @Autowired private CustomerRepo customerRepo;
    @Override
    public Customer createCustomer(Customer customer) {

        try {
            System.out.println("customer saving..."+new ObjectMapper().writeValueAsString(customer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customerRepo.save(customer);
    }

    @Override
    public Customer findCustomer(long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("customer not found|"+id));
    }

    @Override
    public Customer findCustomer(String email) {
        return customerRepo.findCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("customer not found|"+email));

    }
}
