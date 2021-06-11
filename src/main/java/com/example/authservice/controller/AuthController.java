package com.example.authservice.controller;

import com.example.authservice.dao.entities.Customer;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping(value = "/authorize", produces = "application/json")
    public Object autherizaCustomer(@RequestBody String requestBody){
        return authService.authorizeCustomer(requestBody);
    }

    @PostMapping(value = "/register", produces = "application/json")
    public Object registerCustomer(@RequestBody Customer customer){
        return authService.createCustomer(customer);
    }

    @PostMapping("/checking-validation")
    public Object check(){
        return 200;
    }
}
