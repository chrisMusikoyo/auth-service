package com.example.authservice.hazelcast;

import com.example.authservice.dao.entities.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class HazelCastServiceImpl implements HazelCastService {
    private final static Logger logger = Logger.getLogger(HazelCastServiceImpl.class.getName());

    private final HazelcastInstance hazelcastInstance; // calling the configured bean for handling the bean request

    public HazelCastServiceImpl(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    @Override
    public void storeJwtToken(String consumerKey, String token) {
        IMap<String, String> storeJwtToken = hazelcastInstance.getMap("store_tokens");
        storeJwtToken.put(consumerKey, token);

        System.out.println("token generated successfully");
    }

    @Override
    public boolean validateToken(String consumerKey, String token) {
        IMap<String, String> storeJwtToken = hazelcastInstance.getMap("store_tokens");
        if (!storeJwtToken.containsKey(consumerKey)){
            return false;
        }else{
            return token.equals(storeJwtToken.get(consumerKey));
        }
    }

    @Override
    public void storeLoggedInCustomer(Customer customer, String jwtId) {
        IMap<String, String> storeJwtToken = hazelcastInstance.getMap("logged_in_customers");

        try {
            String customerDetails = new ObjectMapper().writeValueAsString(customer);
            storeJwtToken.put(jwtId, customerDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getCustomer(String jwtId) {
        IMap<String, String> storeJwtToken = hazelcastInstance.getMap("logged_in_customers");

        return storeJwtToken.getOrDefault(jwtId , null);
    }

    @Override
    public void processCustomerDetails(Customer customer, String jwtToken) {
        storeJwtToken(customer.getGuiId() , jwtToken);
        storeLoggedInCustomer(customer , customer.getGuiId());
    }
}
