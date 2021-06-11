package com.example.authservice.dao.repo;


import com.example.authservice.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);
}
