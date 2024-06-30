package com.uj.billswift.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uj.billswift.infra.security.SecurityFilter;
import com.uj.billswift.infra.security.TokenService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CompanyService {
    @Autowired
    TokenService tokenService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SecurityFilter securityFilter;
    
    public Company getCompany(HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        var login = tokenService.validateToken(token);

        Company company = companyRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("Company not found"));

        return company;
    }
}
