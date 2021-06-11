package com.example.authservice.service;

import com.example.authservice.dao.cruds.CustomerCruds;
import com.example.authservice.dao.entities.Customer;
import com.example.authservice.dao.entities.User;
import com.example.authservice.exceptions.InvalidCredentialsException;
import com.example.authservice.hazelcast.HazelCastService;
import com.example.authservice.jwt.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private CustomerCruds customerCruds;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenUtil tokenUtil;
    @Autowired private HazelCastService hazelCastService;
    @Override
    public Object authorizeCustomer(String requestBody) {

        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
        Customer customer = customerCruds.findCustomer(jsonObject.get("email").getAsString());

        // match passwords

        // compare customer passwords
        if (!passwordEncoder.matches(jsonObject.get("password").getAsString(), customer.getPassword())){
            throw new InvalidCredentialsException("invalid credentials");
        }

        // generate jwt and store
        User user = new User();
        user.setUsername(customer.getGuiId());
        user.setEnabled(true);
        user.setPassword(customer.getPassword());
        String tokenGenerator = tokenUtil.generateToken(user);

        // store the generated token to hazelcast instance together with the customer information

        hazelCastService.processCustomerDetails(customer , tokenGenerator);

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("responseCode","00");
        responseObject.addProperty("responseMessage","authentication was successful");

        JsonObject dataObject = new JsonObject();
        dataObject.addProperty("accessToken",tokenGenerator);

        try {
            dataObject.add("customer",
                    JsonParser.parseString(new ObjectMapper().writeValueAsString(customer)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        responseObject.add("data",dataObject);


        return responseObject.toString();
    }

    @Override
    public Object createCustomer(Customer customer) {
        customer.setGuiId(UUID.randomUUID().toString());
        customer.setTrials(0);
        String enc = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(enc);

        Customer registeredCustomer =  customerCruds.createCustomer(customer);

        JsonObject responsObject = new JsonObject();

        responsObject.addProperty("responseCode","00");
        responsObject.addProperty("responseMessage","customer created successfully");
        try {
            responsObject.add("data",
                    JsonParser.parseString(
                            new ObjectMapper().writeValueAsString(registeredCustomer)).getAsJsonObject());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return responsObject.toString();
    }
}
