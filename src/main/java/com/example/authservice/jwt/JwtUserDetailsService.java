package com.example.authservice.jwt;

import com.example.authservice.dao.entities.Customer;
import com.example.authservice.exceptions.InvalidCredentialsException;
import com.example.authservice.hazelcast.HazelCastService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    HazelCastService hazelCastService;

    Gson gson = new Gson();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities;
        String customerData = hazelCastService.getCustomer(s);

        if (customerData == null){
            throw new InvalidCredentialsException("invalid JWT token");

        }

        System.out.println(customerData);
        authorities = AuthorityUtils.commaSeparatedStringToAuthorityList( "ROLE_CUSTOMER");
        JsonObject jsonObject = JsonParser.parseString(customerData).getAsJsonObject();

        return new User(s , jsonObject.get("password").getAsString(), authorities);

    }
}
