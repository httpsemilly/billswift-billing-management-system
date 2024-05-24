package com.uj.billswift.infra.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.uj.billswift.company.Company;
import com.uj.billswift.company.CompanyRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private CompanyRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(company.getEmail(), company.getPassword(), new ArrayList<>());
    }
}
