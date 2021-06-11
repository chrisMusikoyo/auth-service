package com.example.authservice.hazelcast;


import com.example.authservice.dao.entities.Customer;

public interface HazelCastService {
    void storeJwtToken(String consumerKey , String token);

    boolean validateToken(String consumerKey , String token);

    void storeLoggedInCustomer(Customer customer , String jwtId);

    String getCustomer(String jwtId);

    void processCustomerDetails(Customer customer , String jwtToken);
}
